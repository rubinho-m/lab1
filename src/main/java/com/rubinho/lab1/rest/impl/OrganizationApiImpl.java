package com.rubinho.lab1.rest.impl;

import com.rubinho.lab1.dto.OrganizationDto;
import com.rubinho.lab1.rest.OrganizationApi;
import com.rubinho.lab1.services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@Deprecated
public class OrganizationApiImpl implements OrganizationApi {
    private final OrganizationService organizationService;

    @Autowired
    public OrganizationApiImpl(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @Override
    public ResponseEntity<OrganizationDto> createOrganization(OrganizationDto organizationDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(organizationService.createOrganization(organizationDto));
    }

    @Override
    public ResponseEntity<OrganizationDto> getOrganizationById(Long id) {
        return ResponseEntity.ok(organizationService.getOrganizationById(id));
    }

    @Override
    public ResponseEntity<List<OrganizationDto>> getAllOrganizations(int page, int limit) {
        return ResponseEntity.ok(organizationService.getAllOrganizations(PageRequest.of(page, limit)));
    }

    @Override
    public ResponseEntity<OrganizationDto> updateOrganization(OrganizationDto organizationDto) {
        return ResponseEntity.accepted().body(organizationService.updateOrganization(organizationDto));
    }

    @Override
    public ResponseEntity<Void> deleteOrganization(Long id) {
        organizationService.deleteOrganizationById(id);
        return ResponseEntity.noContent().build();
    }
}
