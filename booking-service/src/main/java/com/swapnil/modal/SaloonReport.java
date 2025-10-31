package com.swapnil.modal;

import lombok.Data;

@Data
public class SaloonReport {

    private Long saloonId;

    private String saloonName;

    private Integer totalBookings;

    private Integer cancelledBookings;

    private Integer totalServicesCompleted;

    private Integer totalServicesCancelled;
}
