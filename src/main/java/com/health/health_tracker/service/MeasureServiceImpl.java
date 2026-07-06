package com.health.health_tracker.service;

import com.health.health_tracker.exception.ResourceNotFoundException;
import com.health.health_tracker.model.Measure;
import com.health.health_tracker.model.User;
import com.health.health_tracker.repository.MeasureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implémentation des règles métier liées aux mesures de santé.
 */
@Service
@Transactional
public class MeasureServiceImpl implements MeasureService {

    private final MeasureRepository measureRepository;
    private final UserService userService;

    public MeasureServiceImpl(MeasureRepository measureRepository, UserService userService) {
        this.measureRepository = measureRepository;
        this.userService = userService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Measure> findAll() {
        return measureRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Measure> findByUser(Long userId) {
        return measureRepository.findByUserIdOrderByDateDesc(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Measure findById(Long id) {
        return measureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mesure", id));
    }

    @Override
    public Measure saveForUser(Long userId, Measure measure) {
        User user = userService.findById(userId);
        measure.setUser(user);
        return measureRepository.save(measure);
    }

    @Override
    public void deleteById(Long id) {
        if (!measureRepository.existsById(id)) {
            throw new ResourceNotFoundException("Mesure", id);
        }
        measureRepository.deleteById(id);
    }
}
