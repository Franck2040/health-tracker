# Architecture — Health Tracker

Ce document décrit la structure interne de l'application et illustre le flux
des données à travers les couches, à l'aide de diagrammes Mermaid.

## Vue en couches

L'application suit une architecture en couches classique de Spring Boot :

```
Navigateur (Thymeleaf HTML)
        │
        ▼
   Controller   ← reçoit les requêtes HTTP, valide les entrées
        │
        ▼
    Service     ← contient la logique métier, gère les transactions
        │
        ▼
   Repository   ← accès aux données (Spring Data JPA)
        │
        ▼
   Base H2      ← persistance SQL
```

## Diagramme de classes

Relations One-to-Many entre `User` et ses entités enfants (`Activity`,
`Measure`, `Medication`). Un utilisateur possède plusieurs activités, mesures
et traitements ; chaque enfant appartient à un seul utilisateur.

```mermaid
classDiagram
    class User {
        +Long id
        +String firstName
        +String lastName
        +String email
        +Integer age
        +Double weight
        +Double height
        +getBmi() Double
        +getFullName() String
    }

    class Activity {
        +Long id
        +String type
        +Integer durationMinutes
        +Integer caloriesBurned
        +LocalDate date
    }

    class Measure {
        +Long id
        +Double weight
        +Integer systolic
        +Integer diastolic
        +Integer heartRate
        +LocalDate date
        +getBloodPressure() String
    }

    class Medication {
        +Long id
        +String name
        +String dosage
        +String frequency
        +LocalDate startDate
        +LocalDate endDate
        +isActiveOn(LocalDate) boolean
    }

    User "1" --> "0..*" Activity : possède
    User "1" --> "0..*" Measure : possède
    User "1" --> "0..*" Medication : possède
```

## Diagramme de séquence — enregistrement d'une mesure

Flux complet lorsqu'un utilisateur enregistre une mesure de santé depuis le
formulaire, en traversant Contrôleur → Service → Repository.

```mermaid
sequenceDiagram
    actor Utilisateur
    participant V as Vue (measures.html)
    participant C as MeasureController
    participant S as MeasureService
    participant US as UserService
    participant R as MeasureRepository
    participant DB as Base H2

    Utilisateur->>V: Remplit le formulaire et clique « Ajouter »
    V->>C: POST /measures (userId + champs de la mesure)
    C->>C: Validation Jakarta (@NotNull, @Min, @PastOrPresent)
    alt Données invalides
        C-->>V: Réaffiche le formulaire avec les messages d'erreur
    else Données valides
        C->>S: saveForUser(userId, measure)
        S->>US: findById(userId)
        US->>R: (via UserRepository) findById(userId)
        US-->>S: User trouvé
        S->>S: measure.setUser(user)
        S->>R: save(measure)
        R->>DB: INSERT INTO measures ...
        DB-->>R: Mesure persistée (id généré)
        R-->>S: Measure sauvegardée
        S-->>C: Measure sauvegardée
        C-->>V: redirect:/measures (PRG pattern)
        V-->>Utilisateur: Liste mise à jour
    end
```
