package com.rubinho.lab1.services;


import com.rubinho.lab1.dto.AddressDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AddressService {
    AddressDto createAddress(AddressDto addressDto);

    AddressDto getAddressById(long id);

    List<AddressDto> getAllAddresses(Pageable paging);

    AddressDto updateAddress(AddressDto addressDto);

    void deleteAddressById(long id);
}
