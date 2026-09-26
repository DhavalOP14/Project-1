package com.demandforecast.backend.repository;

import com.demandforecast.backend.entity.ReorderRecommendation;
import com.demandforecast.backend.enums.RecommendationStatus;
import com.demandforecast.backend.enums.UrgencyLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReorderRecommendationRepository extends JpaRepository<ReorderRecommendation, Long> {
    List<ReorderRecommendation> findByStatusOrderByCreatedAtDesc(RecommendationStatus status);
    List<ReorderRecommendation> findByUrgencyOrderByCreatedAtDesc(UrgencyLevel urgency);
    List<ReorderRecommendation> findAllByOrderByCreatedAtDesc();
    long countByStatus(RecommendationStatus status);
}
