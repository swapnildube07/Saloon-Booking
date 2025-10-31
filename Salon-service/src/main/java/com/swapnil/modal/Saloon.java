package com.swapnil.modal;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Getter
@Setter
public class Saloon {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column(nullable = false)
    private String name;

    private String phone;

    @ElementCollection
    private List<String> images;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String email;


    @Column(nullable = false)
    private Long ownerId;

    private LocalDateTime openTime;

    private LocalDateTime closeTime;
}
