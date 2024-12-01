package com.rubinho.lab1.services;

import com.rubinho.lab1.dto.ImportAuditDto;
import com.rubinho.lab1.model.User;

import java.util.List;

public interface ImportAuditService {
    void addImportAudit(ImportAuditDto importAuditDto, User user);

    List<ImportAuditDto> getAllImportAudits(User user);
}
