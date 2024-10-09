package com.rubinho.lab1.services;

import com.rubinho.lab1.dto.LocationDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Deprecated
public interface LocationService {
    void createLocation(LocationDto locationDto);

    LocationDto getLocationById(long id);

    List<LocationDto> getAllLocations(Pageable paging);

    void updateLocation(LocationDto locationDto);

    void deleteLocationById(long id);
}
