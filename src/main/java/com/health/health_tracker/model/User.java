package com.health.health_tracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente un utilisateur suivi par l'application.
 *
 * <p>Un utilisateur est la racine de l'agrégat métier : il possède ses activités,
 * ses mesures de santé et ses traitements médicamenteux (relations One-to-Many).
 * Les contraintes de validation Jakarta garantissent l'intégrité des données
 * saisies avant toute persistance.</p>
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le prénom est obligatoire")
    @Size(max = 60, message = "Le prénom ne peut pas dépasser 60 caractères")
    @Column(nullable = false, length = 60)
    private String firstName;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 60, message = "Le nom ne peut pas dépasser 60 caractères")
    @Column(nullable = false, length = 60)
    private String lastName;

    @NotBlank(message = "L'adresse e-mail est obligatoire")
    @Email(message = "Le format de l'adresse e-mail est invalide")
    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @NotNull(message = "L'âge est obligatoire")
    @Min(value = 0, message = "L'âge ne peut pas être négatif")
    @Max(value = 130, message = "L'âge saisi est irréaliste")
    private Integer age;

    /** Poids en kilogrammes. */
    @NotNull(message = "Le poids est obligatoire")
    @DecimalMin(value = "20.0", message = "Le poids doit être supérieur à 20 kg")
    @DecimalMax(value = "400.0", message = "Le poids saisi est irréaliste")
    private Double weight;

    /** Taille en centimètres. */
    @NotNull(message = "La taille est obligatoire")
    @DecimalMin(value = "50.0", message = "La taille doit être supérieure à 50 cm")
    @DecimalMax(value = "260.0", message = "La taille saisie est irréaliste")
    private Double height;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Activity> activities = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Measure> measures = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Medication> medications = new ArrayList<>();

    public User() {
    }

    public User(String firstName, String lastName, String email, Integer age, Double weight, Double height) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
        this.weight = weight;
        this.height = height;
    }

    /**
     * Calcule l'Indice de Masse Corporelle (IMC) à partir du poids et de la taille.
     * Retourne {@code null} si les données ne permettent pas le calcul.
     */
    @Transient
    public Double getBmi() {
        if (weight == null || height == null || height <= 0) {
            return null;
        }
        double heightInMeters = height / 100.0;
        return Math.round((weight / (heightInMeters * heightInMeters)) * 10.0) / 10.0;
    }

    @Transient
    public String getFullName() {
        return firstName + " " + lastName;
    }

    // --- Méthodes utilitaires pour maintenir la cohérence bidirectionnelle ---

    public void addActivity(Activity activity) {
        activities.add(activity);
        activity.setUser(this);
    }

    public void addMeasure(Measure measure) {
        measures.add(measure);
        measure.setUser(this);
    }

    public void addMedication(Medication medication) {
        medications.add(medication);
        medication.setUser(this);
    }

    // --- Getters / Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public List<Measure> getMeasures() {
        return measures;
    }

    public void setMeasures(List<Measure> measures) {
        this.measures = measures;
    }

    public List<Medication> getMedications() {
        return medications;
    }

    public void setMedications(List<Medication> medications) {
        this.medications = medications;
    }
}
