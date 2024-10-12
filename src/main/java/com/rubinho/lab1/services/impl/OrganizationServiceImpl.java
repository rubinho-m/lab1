package com.rubinho.lab1.services.impl;

import com.rubinho.lab1.dto.OrganizationDto;
import com.rubinho.lab1.mappers.OrganizationMapper;
import com.rubinho.lab1.repository.OrganizationRepository;
import com.rubinho.lab1.services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final OrganizationMapper organizationMapper;

    @Autowired
    public OrganizationServiceImpl(OrganizationRepository organizationRepository, OrganizationMapper organizationMapper) {
        this.organizationRepository = organizationRepository;
        this.organizationMapper = organizationMapper;
    }

    @Override
    public List<OrganizationDto> getAllOrganizations() {
        return organizationRepository.findAll()
                .stream()
                .map(organizationMapper::toDto)
                .toList();
    }
}
