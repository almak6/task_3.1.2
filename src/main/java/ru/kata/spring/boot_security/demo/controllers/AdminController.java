package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImpl userServiceImpl;
    private final RoleServiceImpl roleServiceImpl;

    public AdminController(UserServiceImpl userServiceImpl, RoleServiceImpl roleServiceImpl) {
        this.userServiceImpl = userServiceImpl;
        this.roleServiceImpl = roleServiceImpl;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("users", userServiceImpl.findAll());
        return "/admin/user-list";
    }
    @GetMapping("/create")
    public String createUserForm(User user, Model model) {
        model.addAttribute("roleAdmin", roleServiceImpl.getAdminRole());
        model.addAttribute("roleUser", roleServiceImpl.getUserRole());
        return "/admin/create";
    }
    @PostMapping()
    public String createUser(@ModelAttribute("user") User user) {
        userServiceImpl.saveUser(user);
        return "redirect:/admin";
    }
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userServiceImpl.findByID(id).getRoles().clear();
        userServiceImpl.deleteByID(id);
        return "redirect:/admin";
    }
    @GetMapping("/{id}/edit")
    public String editUserForm(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userServiceImpl.findByID(id));
        model.addAttribute("roleAdmin", roleServiceImpl.getAdminRole());
        model.addAttribute("roleUser", roleServiceImpl.getUserRole());
        return "admin/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") Long id) {
        userServiceImpl.saveUser(user);
        return "redirect:/admin";
    }
}
