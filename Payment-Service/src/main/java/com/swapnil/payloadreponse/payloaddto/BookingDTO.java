package com.swapnil.payloadreponse.payloaddto;

import com.swapnil.Domain.BookingStatus; // ✅ Corrected the package name
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class BookingDTO {

    private Long id;

    private Long saloonId;

    private Long customerId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Set<Long> serviceIds;

    private BookingStatus status = BookingStatus.PENDING;



    private int totalServices;
}
