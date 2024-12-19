package com.github.belousovea.sockwarehouse.service;

import com.github.belousovea.sockwarehouse.model.dto.GoodsDto;
import com.github.belousovea.sockwarehouse.model.dto.GoodsFilterDto;
import org.springframework.web.multipart.MultipartFile;

public interface GoodsService {
    void income(GoodsDto dto);

    void outcome(GoodsDto dto);

    int countFilteredGoods(GoodsFilterDto dto);

    void update(long id, GoodsDto dto);

    void batchInsert(MultipartFile file);
}