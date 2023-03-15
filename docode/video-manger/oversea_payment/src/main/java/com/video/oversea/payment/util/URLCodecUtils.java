package com.video.oversea.payment.util;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public abstract class URLCodecUtils {

    public static String encode(String content, String charset) throws UnsupportedEncodingException {
        String encodeContent = URLEncoder.encode(content, charset);
        encodeContent = StringUtils.replace(encodeContent, "+", "%20");

        return encodeContent;
    }

    public static String decode(String content, String charset) throws UnsupportedEncodingException {
        return URLDecoder.decode(content, charset);
    }

    private URLCodecUtils() {

    }
}
