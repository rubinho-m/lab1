package com.rubinho.lab1.repository;

import com.rubinho.lab1.model.ImportAudit;
import com.rubinho.lab1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImportAuditRepository extends JpaRepository<ImportAudit, Long> {
    List<ImportAudit> findAllByUser(User user);
}
