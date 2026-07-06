package com.health.health_tracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

/**
 * Représente un traitement médicamenteux suivi par un utilisateur :
 * nom du médicament, dosage, fréquence de prise et période de traitement.
 */
@Entity
@Table(name = "medications")
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom du médicament est obligatoire")
    @Size(max = 120)
    @Column(nullable = false, length = 120)
    private String name;

    /** Dosage prescrit, ex. « 500 mg ». */
    @NotBlank(message = "Le dosage est obligatoire")
    @Size(max = 60)
    @Column(nullable = false, length = 60)
    private String dosage;

    /** Fréquence de prise, ex. « 2 fois par jour ». */
    @NotBlank(message = "La fréquence est obligatoire")
    @Size(max = 80)
    @Column(nullable = false, length = 80)
    private String frequency;

    @NotNull(message = "La date de début est obligatoire")
    private LocalDate startDate;

    /** Date de fin du traitement (facultative pour un traitement au long cours). */
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Medication() {
    }

    public Medication(String name, String dosage, String frequency, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.dosage = dosage;
        this.frequency = frequency;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Indique si le traitement est encore actif à la date fournie
     * (pas de date de fin, ou date de fin postérieure ou égale à la référence).
     */
    @Transient
    public boolean isActiveOn(LocalDate reference) {
        if (startDate == null || reference.isBefore(startDate)) {
            return false;
        }
        return endDate == null || !reference.isAfter(endDate);
    }

    // --- Getters / Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
