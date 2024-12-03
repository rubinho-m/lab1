package com.rubinho.lab1.services;

import com.rubinho.lab1.dto.ImportAuditDto;
import com.rubinho.lab1.model.ImportAudit;
import com.rubinho.lab1.model.User;

import java.util.List;

public interface ImportAuditService {
    void addImportAudit(ImportAudit importAudit);

    List<ImportAuditDto> getAllImportAudits(User user);
}
