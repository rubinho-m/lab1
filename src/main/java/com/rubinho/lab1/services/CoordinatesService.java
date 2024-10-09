package com.rubinho.lab1.services;

import com.rubinho.lab1.dto.CoordinatesDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Deprecated
public interface CoordinatesService {
    void createCoordinates(CoordinatesDto coordinatesDto);

    CoordinatesDto getCoordinatesById(long id);

    List<CoordinatesDto> getAllCoordinates(Pageable paging);

    void updateCoordinates(CoordinatesDto coordinatesDto);

    void deleteCoordinatesById(long id);
}
