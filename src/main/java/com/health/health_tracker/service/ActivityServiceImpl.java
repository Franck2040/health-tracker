package com.health.health_tracker.service;

import com.health.health_tracker.exception.ResourceNotFoundException;
import com.health.health_tracker.model.Activity;
import com.health.health_tracker.model.User;
import com.health.health_tracker.repository.ActivityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implémentation des règles métier liées aux activités physiques.
 */
@Service
@Transactional
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;
    private final UserService userService;

    public ActivityServiceImpl(ActivityRepository activityRepository, UserService userService) {
        this.activityRepository = activityRepository;
        this.userService = userService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Activity> findAll() {
        return activityRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Activity> findByUser(Long userId) {
        return activityRepository.findByUserIdOrderByDateDesc(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Activity findById(Long id) {
        return activityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Activité", id));
    }

    @Override
    public Activity saveForUser(Long userId, Activity activity) {
        // On récupère l'utilisateur pour garantir la cohérence de la relation
        // et rejeter toute activité rattachée à un utilisateur inexistant.
        User user = userService.findById(userId);
        activity.setUser(user);
        return activityRepository.save(activity);
    }

    @Override
    public void deleteById(Long id) {
        if (!activityRepository.existsById(id)) {
            throw new ResourceNotFoundException("Activité", id);
        }
        activityRepository.deleteById(id);
    }
}
