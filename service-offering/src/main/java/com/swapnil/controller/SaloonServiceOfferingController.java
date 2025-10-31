package com.swapnil.controller;

import com.swapnil.modal.ServiceOffering;
import com.swapnil.payloaddto.CategoryDTO;
import com.swapnil.payloaddto.SaloonDTO;
import com.swapnil.payloaddto.ServiceDTO;
import com.swapnil.service.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/service-offering/saloon-owner")
@RequiredArgsConstructor
public class SaloonServiceOfferingController {
    private final ServiceOfferingService serviceOfferingService;

    @PostMapping
    public ResponseEntity<ServiceOffering> createService(
            @RequestBody ServiceDTO serviceDTO
    ) {
        SaloonDTO saloonDTO = new SaloonDTO();
        saloonDTO.setId(1L);

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(1L);

        ServiceOffering serviceOfferings = serviceOfferingService.createService(
                saloonDTO, categoryDTO, serviceDTO
        );

        return ResponseEntity.ok(serviceOfferings);
    }

    @SneakyThrows
    @PostMapping("/{id}")
    public ResponseEntity<ServiceOffering> updateService(
            @PathVariable("id") Long id,
            @RequestBody ServiceOffering serviceOffering
    ) {
        ServiceOffering updatedService = serviceOfferingService.updateService(id, serviceOffering);
        return ResponseEntity.ok(updatedService);
    }
}
