package com.rubinho.lab1.rest;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface FileApi {
    @GetMapping(value = "file/download/{name}")
    ResponseEntity<Resource> download(@PathVariable("name") String name);
}
