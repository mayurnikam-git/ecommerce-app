package com.example.springoauthdemo.dao;

import com.example.springoauthdemo.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

    /**
     * The method is used to retrieve inventory details for products based on product id.
     *
     * @param productIdList
     * @return
     */
    public List<Inventory> findAllByProductIdIn(List<Integer> productIdList);

    /**
     *
     * @param productId
     * @return
     */
    public Inventory findByProductId(Integer productId);
}
