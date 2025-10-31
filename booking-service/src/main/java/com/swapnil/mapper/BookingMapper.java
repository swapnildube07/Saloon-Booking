package com.swapnil.mapper;

import com.swapnil.modal.Booking;
import com.swapnil.payloaddto.BookingDTO;

public class BookingMapper {
    public static BookingDTO toDTO(Booking booking) {

        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(booking.getId());
        bookingDTO.setCustomerId(booking.getCustomerId());
        bookingDTO.setStatus(booking.getStatus());
      //  bookingDTO.setTotalServices(booking.getTotalServices());
        bookingDTO.setStartTime(booking.getStartTime());
        bookingDTO.setEndTime(booking.getEndTime());
        bookingDTO.setSaloonId(booking.getSaloonId());
        bookingDTO.setServiceIds(booking.getServiceIds());

        return bookingDTO;

    }
}
