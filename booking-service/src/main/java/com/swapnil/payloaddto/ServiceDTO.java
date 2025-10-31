package com.swapnil.payloaddto;

import lombok.Data;

@Data
public class ServiceDTO {
    private Long id;
    private String name;
    private String description;
    private int price;
    private int duration;
    private Long saloonId;
    private Long categoryId;
    private String image;
}
