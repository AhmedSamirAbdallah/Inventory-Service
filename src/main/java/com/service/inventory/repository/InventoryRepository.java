package com.service.inventory.repository;

import com.service.inventory.model.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Boolean existsByProductId(String productId);

    Optional<Inventory> findByProductId(String productId);

}
