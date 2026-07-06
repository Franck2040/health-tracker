package com.health.health_tracker.service;

import com.health.health_tracker.exception.ResourceNotFoundException;
import com.health.health_tracker.model.Medication;
import com.health.health_tracker.model.User;
import com.health.health_tracker.repository.MedicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implémentation des règles métier liées aux traitements médicamenteux.
 */
@Service
@Transactional
public class MedicationServiceImpl implements MedicationService {

    private final MedicationRepository medicationRepository;
    private final UserService userService;

    public MedicationServiceImpl(MedicationRepository medicationRepository, UserService userService) {
        this.medicationRepository = medicationRepository;
        this.userService = userService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Medication> findAll() {
        return medicationRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Medication> findByUser(Long userId) {
        return medicationRepository.findByUserIdOrderByStartDateDesc(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Medication findById(Long id) {
        return medicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Traitement", id));
    }

    @Override
    public Medication saveForUser(Long userId, Medication medication) {
        User user = userService.findById(userId);
        medication.setUser(user);
        return medicationRepository.save(medication);
    }

    @Override
    public void deleteById(Long id) {
        if (!medicationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Traitement", id);
        }
        medicationRepository.deleteById(id);
    }
}
