package com.swapnil.modal;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

@Entity
@Data
public class ServiceOffering {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int duration;

    @Column(nullable = false)
    private Long saloonId;

    @Column(nullable = false)
    private Long categoryId;

    private String image;

}
