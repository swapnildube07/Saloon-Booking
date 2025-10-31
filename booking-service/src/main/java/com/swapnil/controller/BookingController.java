package com.swapnil.controller;

import com.swapnil.domain.BookingStatus;
import com.swapnil.mapper.BookingMapper;
import com.swapnil.modal.Booking;
import com.swapnil.modal.SaloonReport;
import com.swapnil.payloaddto.*;
import com.swapnil.service.Bookingservice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final Bookingservice bookingservice;

    @PostMapping
    public ResponseEntity<Booking> createbooking(
            @RequestParam Long saloonId,
            @RequestBody Bookingrequest bookingrequest
    ) {
        UserDTO user = new UserDTO();
        user.setId(1L);

        SaloonDTO saloon = new SaloonDTO();
        saloon.setId(saloonId);

        LocalDateTime startTime = bookingrequest.getStartTime();

        saloon.setOpenTime(startTime.withHour(9).withMinute(0));
        saloon.setCloseTime(saloon.getOpenTime().plusHours(12));


        Set<ServiceDTO> serviceDTOSet = new HashSet<>();
        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setId(1L);
        serviceDTO.setDuration(45);
        serviceDTO.setPrice(45);
        serviceDTO.setName("Hair Cut 1");
        serviceDTOSet.add(serviceDTO);

        Booking booking = bookingservice.createBooking(
                bookingrequest, user, saloon, serviceDTOSet);

        return ResponseEntity.ok(booking);
    }


    @GetMapping("/customer")
    public ResponseEntity<Set<BookingDTO>> getBookingsByCustomer() {
        List<Booking> bookings = bookingservice.getBookingsByCustomer(1L);
        return ResponseEntity.ok(getBookingDTOs(bookings));
    }

    private Set<BookingDTO> getBookingDTOs(List<Booking> bookings) {
        return bookings.stream()
                .map(BookingMapper::toDTO)
                .collect(Collectors.toSet());
    }

    @GetMapping("/saloon")
    public ResponseEntity<Set<BookingDTO>> getBookingsBySaloon() {
        List<Booking> bookings = bookingservice.getBookingBySaloon(1L);
        return ResponseEntity.ok(getBookingDTOs(bookings));
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDTO> getBookingById(
            @PathVariable Long bookingId
    ) throws Exception {
        Booking booking = bookingservice.getBookingById(bookingId);
        return ResponseEntity.ok(BookingMapper.toDTO(booking));
    }

    @PutMapping("/{bookingId}/status")
    public ResponseEntity<BookingDTO> updateBookingStatus(
            @PathVariable Long bookingId,
            @RequestParam BookingStatus Status
    ) throws Exception {
        Booking booking = bookingservice.updateBooking(bookingId, Status);
        return ResponseEntity.ok(BookingMapper.toDTO(booking));
    }

    @GetMapping("slots/salon/{saloonId}/date/{date}")
    public ResponseEntity<List<BookingSlotDTO>> getBookedSlot(
            @PathVariable Long saloonId,
            @PathVariable LocalDate date
    ) throws Exception {
        List<Booking> booking = bookingservice.getBookingsByDate(date, saloonId);
        List<BookingSlotDTO> slotDTOs = booking.stream().map(booking1 -> {
            BookingSlotDTO slotDTO = new BookingSlotDTO();
            slotDTO.setStartTime(booking1.getStartTime());
            slotDTO.setEndTime(booking1.getEndTime());
            return slotDTO;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(slotDTOs);
    }

    @GetMapping("/report")
    public ResponseEntity<SaloonReport> getSaloonReport() throws Exception {
        SaloonReport report = bookingservice.getSaloonReport(1L);
        return ResponseEntity.ok(report);
    }
}
