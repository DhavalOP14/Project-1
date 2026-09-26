package com.demandforecast.backend.repository;

import com.demandforecast.backend.entity.Inventory;
import com.demandforecast.backend.enums.StockStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByProductId(Long productId);
    List<Inventory> findByStatus(StockStatus status);

    @Query("SELECT COUNT(i) FROM Inventory i WHERE i.status = :status")
    long countByStatus(StockStatus status);

    @Query("SELECT i FROM Inventory i WHERE i.currentStock <= i.reorderPoint")
    List<Inventory> findLowStockOrCritical();
}
