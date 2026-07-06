package com.health.health_tracker.service;

import com.health.health_tracker.model.Activity;

import java.util.List;

/**
 * Contrat métier pour la gestion des activités physiques.
 */
public interface ActivityService {

    List<Activity> findAll();

    List<Activity> findByUser(Long userId);

    Activity findById(Long id);

    /** Enregistre une activité en la rattachant à l'utilisateur cible. */
    Activity saveForUser(Long userId, Activity activity);

    void deleteById(Long id);
}
