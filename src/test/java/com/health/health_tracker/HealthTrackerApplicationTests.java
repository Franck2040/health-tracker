package com.health.health_tracker;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Test de fumée : vérifie que le contexte Spring démarre correctement,
 * que tous les beans (services, repositories, contrôleurs) se câblent
 * et que la configuration JPA est valide.
 */
@SpringBootTest
class HealthTrackerApplicationTests {

    @Test
    void contextLoads() {
    }
}
