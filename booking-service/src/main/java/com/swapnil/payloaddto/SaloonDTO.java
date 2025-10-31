package com.swapnil.payloaddto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SaloonDTO {
    private Long id;
    private String name;
    private String phone;
    private List<String> images;
    private String address;
    private String city;
    private String email;
    private Long ownerId;
    private LocalDateTime openTime;
    private LocalDateTime closeTime;
}
