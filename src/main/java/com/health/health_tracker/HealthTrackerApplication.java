package com.health.health_tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Point d'entrée de l'application Health Tracker.
 * Démarre le conteneur Spring Boot et l'ensemble des composants auto-configurés
 * (serveur web Tomcat embarqué, JPA/Hibernate, moteur Thymeleaf).
 */
@SpringBootApplication
public class HealthTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthTrackerApplication.class, args);
    }
}
