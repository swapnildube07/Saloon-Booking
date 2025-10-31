package com.swapnil.service.impl;

import com.swapnil.domain.BookingStatus;
import com.swapnil.modal.Booking;
import com.swapnil.modal.SaloonReport;
import com.swapnil.payloaddto.Bookingrequest;
import com.swapnil.payloaddto.SaloonDTO;
import com.swapnil.payloaddto.ServiceDTO;
import com.swapnil.payloaddto.UserDTO;
import com.swapnil.repostitory.BookingRepository;
import com.swapnil.service.Bookingservice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements Bookingservice {

    private final BookingRepository bookingRepository;

    @Override
    public Booking createBooking(Bookingrequest booking, UserDTO user, SaloonDTO salon, Set<ServiceDTO> serviceDTOSet) {

        int totalDuration = serviceDTOSet.stream()
                .mapToInt(ServiceDTO::getDuration)
                .sum();

        LocalDateTime bookingStartTime = booking.getStartTime();
        LocalDateTime bookingEndTime = bookingStartTime.plusMinutes(totalDuration);

        try {
            if (!isTimeSlotAvailable(salon, bookingStartTime, bookingEndTime)) {
                throw new Exception("Slot is not available. Please choose a different slot.");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        Set<Long> serviceIds = serviceDTOSet.stream()
                .map(ServiceDTO::getId)
                .collect(Collectors.toSet());

        Booking newBooking = new Booking();
        newBooking.setCustomerId(user.getId());
        newBooking.setSaloonId(salon.getId());
        newBooking.setServiceIds(serviceIds);
        newBooking.setStartTime(bookingStartTime);
        newBooking.setEndTime(bookingEndTime);
        newBooking.setStatus(BookingStatus.PENDING);
        newBooking.setTotalServices(serviceDTOSet.size());

        return bookingRepository.save(newBooking);
    }

    public Boolean isTimeSlotAvailable(SaloonDTO saloonDTO, LocalDateTime bookingStartTime, LocalDateTime bookingEndTime) throws Exception {

        List<Booking> existingBookings = bookingRepository.findBySaloonId(saloonDTO.getId());

        LocalDateTime saloonOpenTime = saloonDTO.getOpenTime()
                .withYear(bookingStartTime.getYear())
                .withMonth(bookingStartTime.getMonthValue())
                .withDayOfMonth(bookingStartTime.getDayOfMonth());

        LocalDateTime saloonCloseTime = saloonDTO.getCloseTime()
                .withYear(bookingEndTime.getYear())
                .withMonth(bookingEndTime.getMonthValue())
                .withDayOfMonth(bookingEndTime.getDayOfMonth());

        if (bookingStartTime.isBefore(saloonOpenTime) || bookingEndTime.isAfter(saloonCloseTime)) {
            throw new Exception("Booking time must be within saloon working hours.");
        }

        for (Booking b : existingBookings) {
            LocalDateTime existingStart = b.getStartTime();
            LocalDateTime existingEnd = b.getEndTime();

            if (bookingStartTime.isEqual(existingStart) ||
                    (bookingStartTime.isBefore(existingEnd) && bookingEndTime.isAfter(existingStart))) {
                throw new Exception("Slot is not available. Please choose a different slot.");
            }
        }

        return true;
    }

    @Override
    public List<Booking> getBookingsByCustomer(Long customerId) {
        return bookingRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Booking> getBookingBySaloonId(Long saloonId) {
        return bookingRepository.findBySaloonId(saloonId);
    }


    @Override
    public List<Booking> getBookingBySaloon(Long saloonId) {
        return bookingRepository.findBySaloonId(saloonId);
    }


    @Override
    public Booking getBookingById(Long id) throws Exception {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new Exception("Booking Not Found"));
    }

    @Override
    public Booking updateBooking(Long bookingId, BookingStatus status) throws Exception {
        Booking booking = getBookingById(bookingId);
        booking.setStatus(status);
        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> getBookingsByDate(LocalDate date, Long saloonId) {
        List<Booking> allBookings = getBookingBySaloonId(saloonId);

        if (date == null) {
            return allBookings;
        }

        return allBookings.stream()
                .filter(booking -> isSameDate(booking.getStartTime(), date))
                .collect(Collectors.toList());
    }

    private boolean isSameDate(LocalDateTime dateTime, LocalDate date) {
        return dateTime.toLocalDate().isEqual(date);
    }

    @Override
    public SaloonReport getSaloonReport(Long saloonId) {
        List<Booking> allBookings = getBookingBySaloonId(saloonId);

        int totalBookings = allBookings.size();

        int totalServicesCompleted = allBookings.stream()
                .filter(b -> b.getStatus() == BookingStatus.CONFIRMED)
                .mapToInt(Booking::getTotalServices)
                .sum();

        int totalServicesCancelled = allBookings.stream()
                .filter(b -> b.getStatus() == BookingStatus.CANCELLED)
                .mapToInt(Booking::getTotalServices)
                .sum();

        long cancelledCount = allBookings.stream()
                .filter(b -> b.getStatus() == BookingStatus.CANCELLED)
                .count();

        SaloonReport report = new SaloonReport();
        report.setSaloonId(saloonId);
        report.setSaloonName("Unknown"); // replace with actual saloon name if needed
        report.setTotalBookings(totalBookings);
        report.setCancelledBookings((int) cancelledCount);
        report.setTotalServicesCompleted(totalServicesCompleted);
        report.setTotalServicesCancelled(totalServicesCancelled);

        return report;
    }
}
