package com.rubinho.lab1.rest.impl;

import com.rubinho.lab1.rest.FileApi;
import com.rubinho.lab1.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileApiImpl implements FileApi {
    private final S3Service s3Service;

    @Autowired
    public FileApiImpl(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @Override
    public ResponseEntity<Resource> download(String name) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        final ContentDisposition contentDisposition = ContentDisposition
                .builder("attachment")
                .filename(name)
                .build();
        httpHeaders.setContentDisposition(contentDisposition);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .contentType(MediaType.valueOf("text/yaml"))
                .body(new ByteArrayResource(s3Service.download(name)));
    }
}
