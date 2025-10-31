package com.swapnil.payloaddto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class Bookingrequest {
    private LocalDateTime startTime;  // Added missing field
    private LocalDate endDate;
    private Set<Long> serviceIds;
}
