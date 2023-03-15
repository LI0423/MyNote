package com.video.oversea.user.config;

import com.video.oversea.user.interceptor.AdminInterceptor;
import com.video.oversea.user.interceptor.ApiIntercepter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.util.List;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private ApiIntercepter apiIntercepter;

    private AdminInterceptor adminInterceptor;

    @Autowired
    public InterceptorConfig(ApiIntercepter apiIntercepter,
                             AdminInterceptor adminInterceptor) {
        this.apiIntercepter = apiIntercepter;
        this.adminInterceptor = adminInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration apiInterceptorRegistration =
                registry.addInterceptor(apiIntercepter);
        apiInterceptorRegistration.addPathPatterns("/api/v1/**");

        InterceptorRegistration adminInterceptorRegistration =
                registry.addInterceptor(adminInterceptor);
        adminInterceptorRegistration.addPathPatterns("/api/v1/**");

        adminInterceptorRegistration.excludePathPatterns("/api/v1/users/login");
        adminInterceptorRegistration.excludePathPatterns("/api/v1/googleUsers/login");
        adminInterceptorRegistration.excludePathPatterns("/api/v1/users");
        adminInterceptorRegistration.excludePathPatterns("/api/v1/users/{id:[0-9]+}");
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 解决controller返回字符串中文乱码问题
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                ((MappingJackson2HttpMessageConverter) converter).setDefaultCharset(Charset.forName("UTF-8"));
            }
        }
    }
}
