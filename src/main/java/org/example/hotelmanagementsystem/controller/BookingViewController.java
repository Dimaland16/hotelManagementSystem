package org.example.hotelmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.hotelmanagementsystem.dto.booking.BookingCreateDto;
import org.example.hotelmanagementsystem.dto.booking.BookingResponseDto;
import org.example.hotelmanagementsystem.dto.booking.BookingUpdateDto;
import org.example.hotelmanagementsystem.service.RoomTypeService;
import org.example.hotelmanagementsystem.service.impl.BookingService;
import org.example.hotelmanagementsystem.service.impl.RoomService;
import org.example.hotelmanagementsystem.service.impl.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingViewController {

    private final RoomTypeService roomTypeService;
    private final BookingService bookingService;
    private final UserService userService;
    private final RoomService roomService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("bookings", bookingService.getAll());
        model.addAttribute("users", userService.getAll());
        model.addAttribute("rooms", roomService.getAll());
        return "booking-list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("bookingDto", new BookingCreateDto());
        model.addAttribute("users", userService.getAll());
        model.addAttribute("rooms", roomService.getAll());
        model.addAttribute("mode", "create");
        model.addAttribute("roomTypes", roomTypeService.getAll());

        return "booking-form";
    }

    @PostMapping
    public String create(@ModelAttribute("bookingDto") BookingCreateDto dto) {
        bookingService.create(dto);
        return "redirect:/bookings";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        BookingResponseDto responseDto = bookingService.getById(id);
        BookingUpdateDto updateDto = new BookingUpdateDto();
        updateDto.setId(responseDto.getId());
        updateDto.setCheckInDate(responseDto.getCheckInDate());
        updateDto.setCheckOutDate(responseDto.getCheckOutDate());
        updateDto.setPaidAmount(responseDto.getPaidAmount());
        updateDto.setStatus(responseDto.getStatus());
        updateDto.setGuestId(responseDto.getGuestId());
        updateDto.setRoomId(responseDto.getRoomId());
        updateDto.setRoomTypeId(responseDto.getRoomTypeId());


        model.addAttribute("bookingDto", updateDto);
        model.addAttribute("users", userService.getAll());
        model.addAttribute("user", userService.getById(responseDto.getGuestId()));
        model.addAttribute("rooms", roomService.getAll());
        model.addAttribute("mode", "edit");
        model.addAttribute("roomTypes", roomTypeService.getAll());

        return "booking-form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute("bookingDto") BookingUpdateDto dto) {
        bookingService.update(id, dto);
        return "redirect:/bookings";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        bookingService.deleteById(id);
        return "redirect:/bookings";
    }
}
