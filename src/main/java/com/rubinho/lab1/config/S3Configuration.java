package com.rubinho.lab1.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Configuration {
    @Bean
    MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint("http://192.168.1.101:9000")
                .credentials("minioadmin", "minioadmin")
                .build();
    }
}
