package com.rubinho.lab1.rest;

import com.rubinho.lab1.dto.OrganizationDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface OrganizationApi {
    @Deprecated
    @PostMapping(value = "/organizations", consumes = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
    ResponseEntity<OrganizationDto> createOrganization(@RequestBody OrganizationDto organizationDto);

    @Deprecated
    @GetMapping("/organizations/{id}")
    ResponseEntity<OrganizationDto> getOrganizationById(@PathVariable("id") Long id);

    @GetMapping("/organizations")
    ResponseEntity<List<OrganizationDto>> getAllOrganizations(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "5") int limit);
    @Deprecated
    @PutMapping(value = "/organizations", consumes = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
    ResponseEntity<OrganizationDto> updateOrganization(@RequestBody OrganizationDto organizationDto);

    @Deprecated
    @DeleteMapping("/organizations/{id}")
    ResponseEntity<Void> deleteOrganization(@PathVariable("id") Long id);
}
