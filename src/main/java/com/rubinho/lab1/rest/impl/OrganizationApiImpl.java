package com.rubinho.lab1.rest.impl;

import com.rubinho.lab1.dto.OrganizationDto;
import com.rubinho.lab1.rest.OrganizationApi;
import com.rubinho.lab1.services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
public class OrganizationApiImpl implements OrganizationApi {
    private final OrganizationService organizationService;

    @Autowired
    public OrganizationApiImpl(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @Override
    public ResponseEntity<List<OrganizationDto>> getAllOrganizations() {
        return ResponseEntity.ok(organizationService.getAllOrganizations());
    }
}
