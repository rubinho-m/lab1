package com.rubinho.lab1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class ImportAuditDto {
    private Long id;
    private Boolean status;
    private UserDto user;
    private Integer count;
}
