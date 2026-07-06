package com.health.health_tracker.repository;

import com.health.health_tracker.model.Measure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Accès aux données des mesures de santé.
 */
@Repository
public interface MeasureRepository extends JpaRepository<Measure, Long> {

    /** Renvoie les relevés d'un utilisateur, les plus récents d'abord. */
    List<Measure> findByUserIdOrderByDateDesc(Long userId);
}
