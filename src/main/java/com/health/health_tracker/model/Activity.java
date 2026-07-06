package com.health.health_tracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

/**
 * Représente une séance d'activité physique enregistrée par un utilisateur
 * (course, marche, vélo, natation...).
 */
@Entity
@Table(name = "activities")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le type d'activité est obligatoire")
    @Size(max = 80)
    @Column(nullable = false, length = 80)
    private String type;

    /** Durée de la séance en minutes. */
    @NotNull(message = "La durée est obligatoire")
    @Min(value = 1, message = "La durée doit être d'au moins 1 minute")
    @Max(value = 1440, message = "La durée ne peut pas dépasser 24 heures")
    private Integer durationMinutes;

    /** Calories dépensées durant la séance. */
    @NotNull(message = "Les calories dépensées sont obligatoires")
    @Min(value = 0, message = "Les calories ne peuvent pas être négatives")
    @Max(value = 20000, message = "La valeur de calories saisie est irréaliste")
    private Integer caloriesBurned;

    @NotNull(message = "La date est obligatoire")
    @PastOrPresent(message = "La date ne peut pas être dans le futur")
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Activity() {
    }

    public Activity(String type, Integer durationMinutes, Integer caloriesBurned, LocalDate date) {
        this.type = type;
        this.durationMinutes = durationMinutes;
        this.caloriesBurned = caloriesBurned;
        this.date = date;
    }

    // --- Getters / Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public Integer getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(Integer caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
