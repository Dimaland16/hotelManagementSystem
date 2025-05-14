package org.example.hotelmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.hotelmanagementsystem.dto.roomType.RoomTypeCreateDto;
import org.example.hotelmanagementsystem.dto.roomType.RoomTypeResponseDto;
import org.example.hotelmanagementsystem.dto.roomType.RoomTypeUpdateDto;
import org.example.hotelmanagementsystem.service.AmenityService;
import org.example.hotelmanagementsystem.service.BedTypeService;
import org.example.hotelmanagementsystem.service.RoomTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/room-types")
@RequiredArgsConstructor
public class RoomTypeViewController {

    private final RoomTypeService roomTypeService;
    private final BedTypeService bedTypeService;
    private final AmenityService amenityService;

    // Отображение списка
    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("roomTypes", roomTypeService.getAll());
        return "room-type-list";
    }

    // Форма добавления
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("roomTypeDto", new RoomTypeCreateDto());
        model.addAttribute("mode", "create");
        model.addAttribute("bedTypes", bedTypeService.getAll());
        model.addAttribute("amenities", amenityService.getAll());
        return "room-type-form";
    }

    // Сохранение нового типа
    @PostMapping
    public String create(@ModelAttribute RoomTypeCreateDto dto) {
        roomTypeService.create(dto);
        return "redirect:/room-types";
    }

    // Форма редактирования
    @GetMapping("/{id}/edit")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        RoomTypeUpdateDto dto = roomTypeService.getUpdateDtoById(id);
        model.addAttribute("roomTypeDto", dto);
        model.addAttribute("mode", "edit");
        model.addAttribute("bedTypes", bedTypeService.getAll());
        model.addAttribute("amenities", amenityService.getAll());
        return "room-type-form";
    }

    // Обновление
    @PostMapping("/{id}")
    public String update(@ModelAttribute RoomTypeUpdateDto dto) {
        roomTypeService.update(dto);
        return "redirect:/room-types";
    }

    // Удаление
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        roomTypeService.deleteById(id);
        return "redirect:/room-types";
    }
}