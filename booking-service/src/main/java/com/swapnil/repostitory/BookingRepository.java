package com.swapnil.repostitory;

import com.swapnil.modal.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByCustomerId(Long customerId);
    List<Booking> findBySaloonId(Long saloonId);



}
