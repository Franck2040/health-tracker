package com.health.health_tracker.service;

import com.health.health_tracker.model.User;

import java.util.List;

/**
 * Contrat métier pour la gestion des utilisateurs.
 * L'interface découple les contrôleurs de l'implémentation concrète (principe DIP).
 */
public interface UserService {

    List<User> findAll();

    User findById(Long id);

    User save(User user);

    void deleteById(Long id);

    long count();
}
