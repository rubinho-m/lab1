package com.rubinho.lab1.rest;

import com.rubinho.lab1.dto.ImportAuditDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

public interface ImportAuditApi {
    @GetMapping("/imports")
    ResponseEntity<List<ImportAuditDto>> getAllImports(@RequestHeader(name = "Authorization") String token);
}
