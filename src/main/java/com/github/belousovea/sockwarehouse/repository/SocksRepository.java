package com.github.belousovea.sockwarehouse.repository;

import com.github.belousovea.sockwarehouse.model.entity.Socks;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface SocksRepository extends JpaRepository<Socks, Long> {

    List<Socks> findByColorAndCottonContent(String color, int cottonContent);

    @Query(value = "SELECT SUM(s.quantity) FROM Socks s WHERE :spec", nativeQuery = true)
    Long sumQuantity(Specification<Socks> spec);

    Collection<Socks> findAll(Specification<Socks> socksSpecification);
}
