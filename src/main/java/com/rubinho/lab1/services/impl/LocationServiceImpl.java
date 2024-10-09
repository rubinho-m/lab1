package com.rubinho.lab1.services.impl;

import com.rubinho.lab1.dto.LocationDto;
import com.rubinho.lab1.mappers.LocationMapper;
import com.rubinho.lab1.repository.LocationRepository;
import com.rubinho.lab1.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Deprecated
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository, LocationMapper locationMapper) {
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
    }

    @Override
    public void createLocation(LocationDto locationDto) {
        locationRepository.save(locationMapper.toEntity(locationDto));
    }

    @Override
    public LocationDto getLocationById(long id) {
        return locationMapper.toDto(locationRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No location found by this id")));
    }

    @Override
    public List<LocationDto> getAllLocations(Pageable paging) {
        return locationRepository.findAll(paging)
                .stream()
                .map(locationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateLocation(LocationDto locationDto) {
        locationRepository.save(locationMapper.toEntity(locationDto));
    }

    @Override
    public void deleteLocationById(long id) {
        locationRepository.deleteById(id);
    }
}
