package com.health.health_tracker.exception;

/**
 * Levée lorsqu'une ressource demandée (utilisateur, activité, mesure...)
 * est introuvable en base. Traduite en réponse HTTP 404 par le gestionnaire global.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resource, Long id) {
        super(resource + " introuvable pour l'identifiant " + id);
    }
}
