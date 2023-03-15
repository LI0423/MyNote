package com.video.oversea.payment.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

@Slf4j
public class HttpClientUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.configure(MapperFeature.USE_WRAPPER_NAME_AS_PROPERTY_NAME, true);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, Boolean.FALSE);
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    private static String doHttp(HttpClientBuilder builder, HttpRequestBase request) throws IOException {
        try (CloseableHttpClient httpClient = builder.build()) {
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity, "utf-8");
                EntityUtils.consume(entity);
                log.info("request url: {}", request.getURI());
                return result;
            }
        }
    }

    /**
     * 每次调用都单独指定证书，需要单独创建HttpClient，不使用Http连接池
     * @param request
     * @param certContent
     * @param password
     * @return
     * @throws KeyStoreException
     * @throws UnrecoverableKeyException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @throws IOException
     * @throws CertificateException
     */
    private static String doHttps(HttpRequestBase request, byte[] certContent, char[] password)
            throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException,
            IOException, CertificateException {

        // 构造ssl context
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        InputStream certInputStream = new ByteArrayInputStream(certContent);
        keyStore.load(certInputStream, password);
        SSLContext sc = SSLContexts.custom().loadKeyMaterial(keyStore, password).build();

        try (CloseableHttpClient httpClient = HttpClients.custom().setSSLContext(sc).build()) {
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity, "utf-8");
                EntityUtils.consume(entity);
                log.info("request url: {}", request.getURI());
                return result;
            }
        }
    }

    public static String doGet(HttpClientBuilder builder, String url) throws IOException, IllegalArgumentException {
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader(HttpHeaders.CONTENT_TYPE, "text/html;charset=UTF-8");
        return doHttp(builder, httpGet);
    }

    public static <T> T doGet(HttpClientBuilder builder, String url, Class<T> clazz) throws IOException, IllegalArgumentException {
        return OBJECT_MAPPER.readValue(doGet(builder, url), clazz);
    }

    public static String doPost(HttpClientBuilder builder, String url, String body) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        HttpEntity entity = new StringEntity(body);
        httpPost.setEntity(entity);

        return doHttp(builder, httpPost);
    }

    public static String doPosts(String url, String body, byte[] certContent, String password)
            throws IOException, CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException,
            KeyStoreException, KeyManagementException {

        HttpPost httpPost = new HttpPost(url);
        HttpEntity entity = new StringEntity(body);
        httpPost.setEntity(entity);

        return doHttps(httpPost, certContent, password.toCharArray());
    }

    public static String doPosts(String url, byte[] body, byte[] certContent, String password)
            throws IOException, CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException,
            KeyStoreException, KeyManagementException {

        HttpPost httpPost = new HttpPost(url);
        HttpEntity entity = new ByteArrayEntity(body);
        httpPost.setEntity(entity);

        return doHttps(httpPost, certContent, password.toCharArray());
    }

    public static String doPosts(String url, String body, byte[] certContent, char[] password)
            throws IOException, CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException,
            KeyStoreException, KeyManagementException {

        HttpPost httpPost = new HttpPost(url);
        HttpEntity entity = new StringEntity(body);
        httpPost.setEntity(entity);

        return doHttps(httpPost, certContent, password);
    }
}
