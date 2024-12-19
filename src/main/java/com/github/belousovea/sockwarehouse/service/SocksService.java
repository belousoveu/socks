package com.github.belousovea.sockwarehouse.service;

import com.github.belousovea.sockwarehouse.model.dto.GoodsDto;
import com.github.belousovea.sockwarehouse.model.dto.GoodsFilterDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SocksService implements GoodsService {

    @Override
    public void income(GoodsDto dto) {

    }

    @Override
    public void outcome(GoodsDto dto) {

    }

    @Override
    public int countFilteredGoods(GoodsFilterDto dto) {
        return 0;
    }

    @Override
    public void update(long id, GoodsDto dto) {

    }

    @Override
    public void batchInsert(MultipartFile file) {

    }
}
