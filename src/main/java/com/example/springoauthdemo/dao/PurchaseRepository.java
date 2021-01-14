package com.example.springoauthdemo.dao;


import com.example.springoauthdemo.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {

    @Override
    public <S extends Purchase> List<S> saveAll(Iterable<S> iterable);
}
