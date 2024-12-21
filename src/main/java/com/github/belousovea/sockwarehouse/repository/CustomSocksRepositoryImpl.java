package com.github.belousovea.sockwarehouse.repository;

import com.github.belousovea.sockwarehouse.model.entity.Socks;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class CustomSocksRepositoryImpl implements CustomSocksRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long sumQuantity(String queryConditional) {
        String query = "select sum(s.quantity) from socks as s" + queryConditional;
        Query nativeQuery = entityManager.createNativeQuery(query, Object.class);
        return nativeQuery.getSingleResult() == null ? 0L : (long) nativeQuery.getSingleResult();
    }

    @Override
    public Collection<Socks> getAllFiltered(String queryConditional) {
        String query = "select * from socks as s" + queryConditional;
        Query nativeQuery = entityManager.createNativeQuery(query, Socks.class);
        return nativeQuery.getResultList();
    }
}
