package com.health.health_tracker.repository;

import com.health.health_tracker.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Accès aux données des activités physiques.
 */
@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    /** Renvoie les activités d'un utilisateur, les plus récentes d'abord. */
    List<Activity> findByUserIdOrderByDateDesc(Long userId);
}
