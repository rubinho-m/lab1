package com.rubinho.lab1.rest.impl;

import com.rubinho.lab1.dto.ImportAuditDto;
import com.rubinho.lab1.model.User;
import com.rubinho.lab1.rest.ImportAuditApi;
import com.rubinho.lab1.services.ImportAuditService;
import com.rubinho.lab1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ImportAuditApiImpl implements ImportAuditApi {
    private final ImportAuditService importAuditService;
    private final UserService userService;

    @Autowired
    public ImportAuditApiImpl(ImportAuditService importAuditService,
                              UserService userService) {
        this.importAuditService = importAuditService;
        this.userService = userService;
    }

    @Override
    public ResponseEntity<List<ImportAuditDto>> getAllImports(String token) {
        final User user = userService.getUserByToken(getToken(token));
        return ResponseEntity.ok(importAuditService.getAllImportAudits(user));
    }

    private String getToken(String bearerToken) {
        return bearerToken.split(" ")[1];
    }
}
