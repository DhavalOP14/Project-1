package com.demandforecast.backend.repository;

import com.demandforecast.backend.entity.DemandForecast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DemandForecastRepository extends JpaRepository<DemandForecast, Long> {
    List<DemandForecast> findByProductIdOrderByForecastDateDesc(Long productId);
    Optional<DemandForecast> findTopByProductIdOrderByForecastDateDesc(Long productId);
    List<DemandForecast> findAllByOrderByForecastDateDesc();
}
