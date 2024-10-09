package com.rubinho.lab1.services;

import com.rubinho.lab1.dto.PersonDto;
import com.rubinho.lab1.model.Role;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminService {
    List<PersonDto> getAllPotentialAdmins(Pageable paging);

    void setNewRoleToPotentialAdmin(Long id, Role role);
}
