package com.health.health_tracker.controller;

import com.health.health_tracker.model.Activity;
import com.health.health_tracker.service.ActivityService;
import com.health.health_tracker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * Contrôleur de gestion des activités physiques.
 */
@Controller
@RequestMapping("/activities")
public class ActivityController {

    private final ActivityService activityService;
    private final UserService userService;

    public ActivityController(ActivityService activityService, UserService userService) {
        this.activityService = activityService;
        this.userService = userService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("activities", activityService.findAll());
        model.addAttribute("users", userService.findAll());
        model.addAttribute("today", LocalDate.now());
        if (!model.containsAttribute("activity")) {
            model.addAttribute("activity", new Activity());
        }
        return "activities";
    }

    @PostMapping
    public String save(@RequestParam Long userId,
                       @Valid @ModelAttribute("activity") Activity activity,
                       BindingResult result,
                       Model model) {
        if (result.hasErrors()) {
            model.addAttribute("activities", activityService.findAll());
            model.addAttribute("users", userService.findAll());
            model.addAttribute("today", LocalDate.now());
            return "activities";
        }
        activityService.saveForUser(userId, activity);
        return "redirect:/activities";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        activityService.deleteById(id);
        return "redirect:/activities";
    }
}
