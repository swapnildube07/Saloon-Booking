package com.swapnil.service;

import com.swapnil.domain.BookingStatus;
import com.swapnil.modal.Booking;
import com.swapnil.modal.SaloonReport;
import com.swapnil.payloaddto.Bookingrequest;
import com.swapnil.payloaddto.SaloonDTO;
import com.swapnil.payloaddto.ServiceDTO;
import com.swapnil.payloaddto.UserDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface Bookingservice {

    Booking createBooking(Bookingrequest booking ,
                          UserDTO user ,
                          SaloonDTO salon ,
                          Set<ServiceDTO> serviceDTOSet);


    List<Booking> getBookingsByCustomer(Long customerId);
    List<Booking> getBookingBySaloonId(Long saloonId);
    List<Booking> getBookingBySaloon(Long saloonId);
    Booking getBookingById(Long id) throws Exception;
    Booking updateBooking(Long bookingId, BookingStatus status) throws Exception;
    List<Booking> getBookingsByDate(LocalDate date,Long saloonId);

    SaloonReport getSaloonReport(Long saloonId);


}
