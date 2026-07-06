package com.health.health_tracker.repository;

import com.health.health_tracker.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Accès aux données des traitements médicamenteux.
 */
@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {

    /** Renvoie les traitements d'un utilisateur, du plus récent au plus ancien. */
    List<Medication> findByUserIdOrderByStartDateDesc(Long userId);
}
