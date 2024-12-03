package com.rubinho.lab1.services.impl;

import com.rubinho.lab1.dto.ImportAuditDto;
import com.rubinho.lab1.mappers.ImportAuditMapper;
import com.rubinho.lab1.model.ImportAudit;
import com.rubinho.lab1.model.Role;
import com.rubinho.lab1.model.User;
import com.rubinho.lab1.repository.ImportAuditRepository;
import com.rubinho.lab1.services.ImportAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImportAuditServiceImpl implements ImportAuditService {
    private final ImportAuditRepository importAuditRepository;
    private final ImportAuditMapper importAuditMapper;

    @Autowired
    public ImportAuditServiceImpl(
            ImportAuditRepository importAuditRepository,
            ImportAuditMapper importAuditMapper
    ) {
        this.importAuditRepository = importAuditRepository;
        this.importAuditMapper = importAuditMapper;
    }

    @Override
    public void addImportAudit(ImportAudit importAudit) {
        importAuditRepository.save(importAudit);
    }

    @Override
    public List<ImportAuditDto> getAllImportAudits(User user) {
        if (user.getRole().equals(Role.ADMIN)) {
            return importAuditRepository.findAll()
                    .stream()
                    .map(importAuditMapper::toDto)
                    .toList();
        }
        return importAuditRepository.findAllByUser(user)
                .stream()
                .map(importAuditMapper::toDto)
                .toList();
    }
}
