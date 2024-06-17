package ru.teymurosman.javacodetest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.teymurosman.javacodetest.dto.OperationDto;
import ru.teymurosman.javacodetest.model.Operation;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OperationMapper {

    Operation toOperation(OperationDto operationDto);
}
