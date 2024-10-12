package com.rubinho.lab1.rest;

import com.rubinho.lab1.dto.OrganizationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface OrganizationApi {
    @GetMapping("/organizations")
    ResponseEntity<List<OrganizationDto>> getAllOrganizations();
}
