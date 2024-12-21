package com.github.belousovea.sockwarehouse.service;

import com.github.belousovea.sockwarehouse.model.dto.GoodsDto;
import com.github.belousovea.sockwarehouse.model.dto.GoodsFilterDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

public interface GoodsService<T extends GoodsDto, F extends GoodsFilterDto> {
    void income(T dto);

    void outcome(T dto);

    long countFilteredGoods(F filterDto);

    void update(long id, T dto);

    void batchInsert(MultipartFile file);

    Collection<T> findFilteredGoods(F fFilterDto);
}