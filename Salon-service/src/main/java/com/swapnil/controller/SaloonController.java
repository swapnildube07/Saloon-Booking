package com.swapnil.controller;

import com.swapnil.mapper.SaloonMapper;
import com.swapnil.modal.Saloon;
import com.swapnil.payloaddto.SaloonDTO;
import com.swapnil.payloaddto.UserDTO;
import com.swapnil.service.SaloonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/saloon")
@RequiredArgsConstructor
public class SaloonController {

    private final SaloonService saloonService;

    @PostMapping
    public ResponseEntity<SaloonDTO> createSaloon(@RequestBody SaloonDTO saloonDTO) {
        UserDTO user = new UserDTO();
        user.setId(1L);  // Set this ID dynamically as required
        Saloon saloon = saloonService.createSaloon(saloonDTO, user);
        SaloonDTO saloonDTO1 = SaloonMapper.mapTODTO(saloon);
        return ResponseEntity.ok(saloonDTO1);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SaloonDTO> updateSaloon(@PathVariable Long id, @RequestBody SaloonDTO saloonDTO) throws Exception {
        UserDTO user = new UserDTO();
        user.setId(1L);  // Set this ID dynamically as required
        Saloon saloon = saloonService.updateSaloon(saloonDTO, user, id);
        SaloonDTO saloonDTO1 = SaloonMapper.mapTODTO(saloon);
        return ResponseEntity.ok(saloonDTO1);
    }

    @GetMapping
    public ResponseEntity<List<SaloonDTO>> getAllSaloon() {
        List<Saloon> saloons = saloonService.getAllSaloon();
        List<SaloonDTO> saloonDTOs = saloons.stream()
                .map(SaloonMapper::mapTODTO)
                .toList();
        return ResponseEntity.ok(saloonDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaloonDTO> getSaloonById(@PathVariable Long id) throws Exception {
        Saloon saloon = saloonService.getSaloonById(id);
        SaloonDTO saloonDTO = SaloonMapper.mapTODTO(saloon);
        return ResponseEntity.ok(saloonDTO);
    }


    @GetMapping("/search")
    public ResponseEntity<List<SaloonDTO>> searchSaloon(@RequestParam("city") String city) {
        List<Saloon> saloons = saloonService.searchSaloonByCity(city);
        List<SaloonDTO> saloonDTOs = saloons.stream()
                .map(SaloonMapper::mapTODTO)
                .toList();
        return ResponseEntity.ok(saloonDTOs);
    }

    @GetMapping("/owner/{id}")
    public ResponseEntity<SaloonDTO> getByOwnerId(@PathVariable Long id) throws Exception {
        Saloon saloon = saloonService.getByOwnerId(id);
        SaloonDTO saloonDTO = SaloonMapper.mapTODTO(saloon);
        return ResponseEntity.ok(saloonDTO);
    }

    // New DELETE method to delete a saloon
}
