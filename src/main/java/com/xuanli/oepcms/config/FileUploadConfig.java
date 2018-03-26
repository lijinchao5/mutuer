package com.xuanli.oepcms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class FileUploadConfig {
    // 上传文件允许的最大大小
    private static final int MAX_SIZE = 2 * 1024 * 1024; // 2MB

//    @Bean
//    public MultipartResolver multipartResolver() {
//        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
//
//        multipartResolver.setMaxUploadSize(MAX_SIZE);
//        multipartResolver.setMaxInMemorySize(MAX_SIZE);
//        multipartResolver.setMaxUploadSize(MAX_SIZE);
//
//        return multipartResolver;
//    }
}