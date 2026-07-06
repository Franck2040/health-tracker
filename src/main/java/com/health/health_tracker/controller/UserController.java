package com.health.health_tracker.controller;

import com.health.health_tracker.model.User;
import com.health.health_tracker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur de gestion des utilisateurs (création, consultation, suppression).
 */
@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userService.findAll());
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }
        return "users";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute("user") User user,
                       BindingResult result,
                       Model model) {
        if (result.hasErrors()) {
            model.addAttribute("users", userService.findAll());
            return "users";
        }
        userService.save(user);
        return "redirect:/users";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        userService.deleteById(id);
        return "redirect:/users";
    }
}
