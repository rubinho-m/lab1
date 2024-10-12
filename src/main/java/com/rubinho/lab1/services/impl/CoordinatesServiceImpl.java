package com.rubinho.lab1.services.impl;

import com.rubinho.lab1.dto.CoordinatesDto;
import com.rubinho.lab1.mappers.CoordinatesMapper;
import com.rubinho.lab1.repository.CoordinatesRepository;
import com.rubinho.lab1.services.CoordinatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoordinatesServiceImpl implements CoordinatesService {
    private final CoordinatesRepository coordinatesRepository;
    private final CoordinatesMapper coordinatesMapper;

    @Autowired
    public CoordinatesServiceImpl(CoordinatesRepository coordinatesRepository, CoordinatesMapper coordinatesMapper) {
        this.coordinatesRepository = coordinatesRepository;
        this.coordinatesMapper = coordinatesMapper;
    }

    @Override
    public List<CoordinatesDto> getAllCoordinates() {
        return coordinatesRepository.findAll()
                .stream()
                .map(coordinatesMapper::toDto)
                .toList();
    }
}
