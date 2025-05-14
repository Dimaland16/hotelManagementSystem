package org.example.hotelmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.hotelmanagementsystem.dto.room.RoomCreateDto;
import org.example.hotelmanagementsystem.dto.room.RoomResponseDto;
import org.example.hotelmanagementsystem.dto.room.RoomUpdateDto;
import org.example.hotelmanagementsystem.service.RoomTypeService;
import org.example.hotelmanagementsystem.service.impl.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Controller
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomViewController {

    private final RoomService roomService;
    private final RoomTypeService roomTypeService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("rooms", roomService.getAll());
        model.addAttribute("roomTypes", roomTypeService.getAll());
        return "room-list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("roomDto", new RoomCreateDto());
        model.addAttribute("roomTypes", roomTypeService.getAll());
        model.addAttribute("mode", "create");
        model.addAttribute("statuses", Arrays.asList("Свободен", "Занят", "Уборка", "В ремонте"));
        return "room-form";
    }

    @PostMapping
    public String create(@ModelAttribute("roomDto") RoomCreateDto dto) {
        roomService.create(dto);
        return "redirect:/rooms";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        RoomResponseDto dto = roomService.getById(id);

        // Преобразуем ResponseDto → UpdateDto
        RoomUpdateDto updateDto = new RoomUpdateDto();
        updateDto.setId(dto.getId());
        updateDto.setRoomNumber(dto.getRoomNumber());
        updateDto.setFloor(dto.getFloor());
        updateDto.setStatus(dto.getStatus());
        updateDto.setRoomTypeId(dto.getRoomTypeId());

        model.addAttribute("roomDto", updateDto);
        model.addAttribute("roomTypes", roomTypeService.getAll());
        model.addAttribute("mode", "edit");
        model.addAttribute("statuses", Arrays.asList("Свободен", "Занят", "Уборка", "В ремонте"));
        return "room-form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute("roomDto") RoomUpdateDto dto) {
        dto.setId(id);
        roomService.update(id, dto);
        return "redirect:/rooms";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        roomService.deleteById(id);
        return "redirect:/rooms";
    }
}
