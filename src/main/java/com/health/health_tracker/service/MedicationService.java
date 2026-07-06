package com.health.health_tracker.service;

import com.health.health_tracker.model.Medication;

import java.util.List;

/**
 * Contrat métier pour la gestion des traitements médicamenteux.
 * (Ajouté pour offrir un suivi complet des médicaments côté tableau de bord.)
 */
public interface MedicationService {

    List<Medication> findAll();

    List<Medication> findByUser(Long userId);

    Medication findById(Long id);

    /** Enregistre un traitement en le rattachant à l'utilisateur cible. */
    Medication saveForUser(Long userId, Medication medication);

    void deleteById(Long id);
}
