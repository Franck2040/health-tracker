package com.health.health_tracker.service;

import com.health.health_tracker.model.Measure;

import java.util.List;

/**
 * Contrat métier pour la gestion des mesures de santé.
 */
public interface MeasureService {

    List<Measure> findAll();

    List<Measure> findByUser(Long userId);

    Measure findById(Long id);

    /** Enregistre un relevé en le rattachant à l'utilisateur cible. */
    Measure saveForUser(Long userId, Measure measure);

    void deleteById(Long id);
}
