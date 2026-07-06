package com.health.health_tracker.controller;

import com.health.health_tracker.model.Measure;
import com.health.health_tracker.service.MeasureService;
import com.health.health_tracker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * Contrôleur de gestion des mesures de santé.
 */
@Controller
@RequestMapping("/measures")
public class MeasureController {

    private final MeasureService measureService;
    private final UserService userService;

    public MeasureController(MeasureService measureService, UserService userService) {
        this.measureService = measureService;
        this.userService = userService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("measures", measureService.findAll());
        model.addAttribute("users", userService.findAll());
        model.addAttribute("today", LocalDate.now());
        if (!model.containsAttribute("measure")) {
            model.addAttribute("measure", new Measure());
        }
        return "measures";
    }

    @PostMapping
    public String save(@RequestParam Long userId,
                       @Valid @ModelAttribute("measure") Measure measure,
                       BindingResult result,
                       Model model) {
        if (result.hasErrors()) {
            model.addAttribute("measures", measureService.findAll());
            model.addAttribute("users", userService.findAll());
            model.addAttribute("today", LocalDate.now());
            return "measures";
        }
        measureService.saveForUser(userId, measure);
        return "redirect:/measures";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        measureService.deleteById(id);
        return "redirect:/measures";
    }
}
