package com.github.belousovea.sockwarehouse.mapper;

import com.github.belousovea.sockwarehouse.model.dto.SocksDto;
import com.github.belousovea.sockwarehouse.model.entity.Socks;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class SocksMapper {

    private final ModelMapper modelMapper;

    public SocksMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Socks toEntity(SocksDto dto) {
        return modelMapper.map(dto, Socks.class);
    }

    public SocksDto toDto(Socks entity) {
        return modelMapper.map(entity, SocksDto.class);
    }
}
