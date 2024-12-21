package com.github.belousovea.sockwarehouse.repository;

import com.github.belousovea.sockwarehouse.model.entity.Socks;

import java.util.Collection;

public interface CustomSocksRepository {

    Long sumQuantity(String queryConditional);

    Collection<Socks> getAllFiltered(String queryConditional);

}
