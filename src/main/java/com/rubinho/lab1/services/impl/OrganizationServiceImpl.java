package com.rubinho.lab1.services.impl;

import com.rubinho.lab1.dto.OrganizationDto;
import com.rubinho.lab1.mappers.OrganizationMapper;
import com.rubinho.lab1.repository.OrganizationRepository;
import com.rubinho.lab1.services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

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
    public OrganizationDto createOrganization(OrganizationDto organizationDto) {
        return organizationMapper.toDto(organizationRepository.save(organizationMapper.toEntity(organizationDto)));
    }

    @Override
    public OrganizationDto getOrganizationById(long id) {
        return organizationMapper.toDto(organizationRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No organization found by this id")));
    }

    @Override
    public List<OrganizationDto> getAllOrganizations(Pageable paging) {
        return organizationRepository.findAll(paging)
                .stream()
                .map(organizationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrganizationDto updateOrganization(OrganizationDto organizationDto) {
        return organizationMapper.toDto(organizationRepository.save(organizationMapper.toEntity(organizationDto)));
    }

    @Override
    public void deleteOrganizationById(long id) {
        organizationRepository.deleteById(id);
    }
}
