package com.rubinho.lab1.repository;

import com.rubinho.lab1.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    boolean existsByFullName(String fullName);
}
