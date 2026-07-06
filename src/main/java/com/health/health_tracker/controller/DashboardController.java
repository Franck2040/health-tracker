package com.health.health_tracker.controller;

import com.health.health_tracker.model.Activity;
import com.health.health_tracker.model.Measure;
import com.health.health_tracker.model.Medication;
import com.health.health_tracker.service.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

/**
 * Contrôleur du tableau de bord.
 *
 * <p>Agrège les indicateurs clés de l'ensemble des utilisateurs (nombre d'activités,
 * de mesures, dernières entrées) et pilote également la page des traitements
 * médicamenteux, qui vient compléter la vue synthétique de santé.</p>
 */
@Controller
public class DashboardController {

    private final UserService userService;
    private final ActivityService activityService;
    private final MeasureService measureService;
    private final MedicationService medicationService;

    public DashboardController(UserService userService,
                               ActivityService activityService,
                               MeasureService measureService,
                               MedicationService medicationService) {
        this.userService = userService;
        this.activityService = activityService;
        this.measureService = measureService;
        this.medicationService = medicationService;
    }

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        List<Activity> activities = activityService.findAll();
        List<Measure> measures = measureService.findAll();

        // Cinq activités les plus récentes, tous utilisateurs confondus.
        List<Activity> recentActivities = activities.stream()
                .sorted(Comparator.comparing(Activity::getDate).reversed())
                .limit(5)
                .toList();

        List<Measure> recentMeasures = measures.stream()
                .sorted(Comparator.comparing(Measure::getDate).reversed())
                .limit(5)
                .toList();

        int totalCalories = activities.stream()
                .mapToInt(Activity::getCaloriesBurned)
                .sum();

        model.addAttribute("userCount", userService.count());
        model.addAttribute("activityCount", activities.size());
        model.addAttribute("measureCount", measures.size());
        model.addAttribute("medicationCount", medicationService.findAll().size());
        model.addAttribute("totalCalories", totalCalories);
        model.addAttribute("recentActivities", recentActivities);
        model.addAttribute("recentMeasures", recentMeasures);
        model.addAttribute("users", userService.findAll());
        return "dashboard";
    }

    // --- Gestion des traitements médicamenteux ---

    @GetMapping("/medications")
    public String medications(Model model) {
        model.addAttribute("medications", medicationService.findAll());
        model.addAttribute("users", userService.findAll());
        model.addAttribute("today", LocalDate.now());
        if (!model.containsAttribute("medication")) {
            model.addAttribute("medication", new Medication());
        }
        return "medications";
    }

    @PostMapping("/medications")
    public String saveMedication(@RequestParam Long userId,
                                 @Valid @ModelAttribute("medication") Medication medication,
                                 BindingResult result,
                                 Model model) {
        if (result.hasErrors()) {
            model.addAttribute("medications", medicationService.findAll());
            model.addAttribute("users", userService.findAll());
            model.addAttribute("today", LocalDate.now());
            return "medications";
        }
        medicationService.saveForUser(userId, medication);
        return "redirect:/medications";
    }

    @PostMapping("/medications/{id}/delete")
    public String deleteMedication(@PathVariable Long id) {
        medicationService.deleteById(id);
        return "redirect:/medications";
    }
}
