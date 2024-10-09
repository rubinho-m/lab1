package com.rubinho.lab1.services.impl;

import com.rubinho.lab1.dto.CoordinatesDto;
import com.rubinho.lab1.mappers.CoordinatesMapper;
import com.rubinho.lab1.services.CoordinatesService;

import com.rubinho.lab1.repository.CoordinatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Deprecated
public class CoordinatesServiceImpl implements CoordinatesService {
    private final CoordinatesRepository coordinatesRepository;
    private final CoordinatesMapper coordinatesMapper;

    @Autowired
    public CoordinatesServiceImpl(CoordinatesRepository coordinatesRepository, CoordinatesMapper coordinatesMapper) {
        this.coordinatesRepository = coordinatesRepository;
        this.coordinatesMapper = coordinatesMapper;
    }

    @Override
    public void createCoordinates(CoordinatesDto coordinatesDto) {
        coordinatesRepository.save(coordinatesMapper.toEntity(coordinatesDto));
    }

    @Override
    public CoordinatesDto getCoordinatesById(long id) {
        return coordinatesMapper.toDto(coordinatesRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No coordinates found by this id")));
    }

    @Override
    public List<CoordinatesDto> getAllCoordinates(Pageable paging) {
        return coordinatesRepository.findAll(paging)
                .stream()
                .map(coordinatesMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateCoordinates(CoordinatesDto coordinatesDto) {
        coordinatesRepository.save(coordinatesMapper.toEntity(coordinatesDto));
    }

    @Override
    public void deleteCoordinatesById(long id) {
        coordinatesRepository.deleteById(id);
    }
}
