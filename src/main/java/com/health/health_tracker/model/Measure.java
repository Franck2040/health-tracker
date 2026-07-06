package com.health.health_tracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

/**
 * Représente un relevé de mesures de santé à une date donnée :
 * poids, tension artérielle (systolique / diastolique) et fréquence cardiaque.
 */
@Entity
@Table(name = "measures")
public class Measure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Poids relevé en kilogrammes. */
    @NotNull(message = "Le poids est obligatoire")
    @DecimalMin(value = "20.0", message = "Le poids doit être supérieur à 20 kg")
    @DecimalMax(value = "400.0", message = "Le poids saisi est irréaliste")
    private Double weight;

    /** Pression artérielle systolique (mmHg) — la valeur haute. */
    @NotNull(message = "La tension systolique est obligatoire")
    @Min(value = 50, message = "La tension systolique saisie est trop basse")
    @Max(value = 300, message = "La tension systolique saisie est irréaliste")
    private Integer systolic;

    /** Pression artérielle diastolique (mmHg) — la valeur basse. */
    @NotNull(message = "La tension diastolique est obligatoire")
    @Min(value = 30, message = "La tension diastolique saisie est trop basse")
    @Max(value = 200, message = "La tension diastolique saisie est irréaliste")
    private Integer diastolic;

    /** Fréquence cardiaque au repos (battements par minute). */
    @NotNull(message = "La fréquence cardiaque est obligatoire")
    @Min(value = 30, message = "La fréquence cardiaque saisie est trop basse")
    @Max(value = 250, message = "La fréquence cardiaque saisie est irréaliste")
    private Integer heartRate;

    @NotNull(message = "La date du relevé est obligatoire")
    @PastOrPresent(message = "La date ne peut pas être dans le futur")
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Measure() {
    }

    public Measure(Double weight, Integer systolic, Integer diastolic, Integer heartRate, LocalDate date) {
        this.weight = weight;
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.heartRate = heartRate;
        this.date = date;
    }

    /**
     * Restitue la tension sous la forme conventionnelle « systolique/diastolique ».
     */
    @Transient
    public String getBloodPressure() {
        if (systolic == null || diastolic == null) {
            return "—";
        }
        return systolic + "/" + diastolic;
    }

    // --- Getters / Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getSystolic() {
        return systolic;
    }

    public void setSystolic(Integer systolic) {
        this.systolic = systolic;
    }

    public Integer getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(Integer diastolic) {
        this.diastolic = diastolic;
    }

    public Integer getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(Integer heartRate) {
        this.heartRate = heartRate;
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
