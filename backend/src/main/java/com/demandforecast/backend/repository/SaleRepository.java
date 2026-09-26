package com.demandforecast.backend.repository;

import com.demandforecast.backend.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    Optional<Sale> findBySaleCode(String saleCode);
    List<Sale> findBySaleDateBetweenOrderBySaleDateDesc(LocalDate startDate, LocalDate endDate);
    List<Sale> findAllByOrderBySaleDateDesc();

    @Query("SELECT si.sale.saleDate AS saleDate, SUM(si.quantity) AS totalQty " +
           "FROM SaleItem si WHERE si.product.id = :productId " +
           "GROUP BY si.sale.saleDate ORDER BY si.sale.saleDate ASC")
    List<Object[]> findDailySalesQuantityByProductId(@Param("productId") Long productId);

    @Query("SELECT SUM(s.totalAmount) FROM Sale s")
    Double calculateTotalSalesRevenue();
}
