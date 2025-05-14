package org.example.hotelmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.hotelmanagementsystem.dto.user.UserCreateDto;
import org.example.hotelmanagementsystem.dto.user.UserUpdateDto;
import org.example.hotelmanagementsystem.service.impl.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserViewController {

    private final UserService userService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("users", userService.getAll());
        return "user-list";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("userDto", userService.getById(id));
        model.addAttribute("mode", "edit");
        return "user-form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute("userDto") UserUpdateDto dto) {
        dto.setId(id);
        userService.update(id, dto);
        return "redirect:/users";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("userDto", new UserCreateDto());
        model.addAttribute("mode", "create");
        return "user-form";
    }

    @PostMapping
    public String create(@ModelAttribute("userDto") UserCreateDto dto) {
        userService.create(dto);
        return "redirect:/users";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        userService.deleteById(id);
        return "redirect:/users";
    }
}