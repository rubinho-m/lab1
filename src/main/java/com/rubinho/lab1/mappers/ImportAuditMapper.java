package com.rubinho.lab1.mappers;

import com.rubinho.lab1.dto.ImportAuditDto;
import com.rubinho.lab1.model.ImportAudit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImportAuditMapper {
    ImportAudit toEntity(ImportAuditDto importAuditDto);

    ImportAuditDto toDto(ImportAudit importAudit);
}
