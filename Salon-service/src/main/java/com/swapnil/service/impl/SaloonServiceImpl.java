package com.swapnil.service.impl;

import com.swapnil.modal.Saloon;
import com.swapnil.payloaddto.SaloonDTO;
import com.swapnil.payloaddto.UserDTO;
import com.swapnil.repositary.SaloonRepository;
import com.swapnil.service.SaloonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaloonServiceImpl implements SaloonService {

    private final SaloonRepository saloonRepository;

    @Override
    public Saloon createSaloon(SaloonDTO saloonDTO, UserDTO user) {
        Saloon saloon = new Saloon();
        saloon.setName(saloonDTO.getName());
        saloon.setPhone(saloonDTO.getPhone());
        saloon.setOwnerId(user.getId());
        saloon.setOpenTime(LocalDateTime.now());
        saloon.setCloseTime(LocalDateTime.now());
        saloon.setImages(saloonDTO.getImages());
        saloon.setAddress(saloonDTO.getAddress());
        saloon.setCity(saloonDTO.getCity());
        saloon.setEmail(saloonDTO.getEmail());

        // Ensure ownerId is set and not null

        // Handle openTime (nullable)
        saloon.setOpenTime(saloonDTO.getOpenTime());

        // Handle closeTime (NOT nullable)
        if (saloonDTO.getCloseTime() != null) {
            saloon.setCloseTime(saloonDTO.getCloseTime());
        } else {
            saloon.setCloseTime(LocalDateTime.now().plusHours(8)); // Default value
        }

        return saloonRepository.save(saloon);
    }

    @Override
    public Saloon updateSaloon(SaloonDTO saloonDTO, UserDTO user, Long saloonId) throws Exception {
        Saloon saloon = saloonRepository.findById(saloonId).orElseThrow(() -> new Exception("Saloon not found"));

        if (!saloon.getOwnerId().equals(user.getId())) {
            throw new Exception("You are not authorized to update this saloon");
        }

        saloon.setName(saloonDTO.getName());
        saloon.setPhone(saloonDTO.getPhone());
        saloon.setImages(saloonDTO.getImages());
        saloon.setAddress(saloonDTO.getAddress());
        saloon.setCity(saloonDTO.getCity());
        saloon.setEmail(saloonDTO.getEmail());

        //Handle openTime
        if(saloonDTO.getOpenTime() != null){
            saloon.setOpenTime(saloonDTO.getOpenTime());
        } else {
            saloon.setOpenTime(LocalDateTime.now().plusHours(8));
        }


        //Handle closeTime
        if(saloonDTO.getCloseTime() != null){
            saloon.setCloseTime(saloonDTO.getCloseTime());
        } else {
            saloon.setCloseTime(LocalDateTime.now().plusHours(8));
        }

        return saloonRepository.save(saloon);
    }

    @Override
    public List<Saloon> getAllSaloon() {
        return saloonRepository.findAll();
    }

    @Override
    public Saloon getSaloonById(Long id) throws Exception {
        return saloonRepository.findById(id).orElseThrow(() -> new Exception("Saloon not found"));
    }

    @Override
    public Saloon getByOwnerId(Long id) throws Exception {
        return saloonRepository.findByOwnerId(id);
    }

    @Override
    public List<Saloon> searchSaloonByCity(String city) {
        return saloonRepository.searchSaloon(city);
    }


}