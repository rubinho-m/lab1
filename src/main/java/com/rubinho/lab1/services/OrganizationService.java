package com.rubinho.lab1.services;

import com.rubinho.lab1.dto.OrganizationDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrganizationService {
    OrganizationDto createOrganization(OrganizationDto organizationDto);

    OrganizationDto getOrganizationById(long id);

    List<OrganizationDto> getAllOrganizations(Pageable paging);

    OrganizationDto updateOrganization(OrganizationDto organizationDto);

    void deleteOrganizationById(long id);
}
