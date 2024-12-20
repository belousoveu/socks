package com.github.belousovea.sockwarehouse.repository;

import com.github.belousovea.sockwarehouse.model.entity.Socks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SocksRepository extends CustomSocksRepository, JpaRepository<Socks, Long> {

    List<Socks> findByColorAndCottonContentOrderById(String color, int cottonContent);

}
