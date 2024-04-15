package com.kirito.excel.mapstruct;

import com.kirito.excel.domain.BigData;
import com.kirito.excel.dto.BigDataDto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BigDataConverter {

    @Named("bigData2Dto")
    BigDataDto bigData2Dto(BigData bigData);

    @IterableMapping(qualifiedByName = "bigData2Dto")
    List<BigDataDto> bigData2DtoList(List<BigData> bigDataList);

}
