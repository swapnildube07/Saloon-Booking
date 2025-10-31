package com.swapnil.controller;


import com.swapnil.modal.ServiceOffering;
import com.swapnil.payloaddto.CategoryDTO;
import com.swapnil.payloaddto.SaloonDTO;
import com.swapnil.payloaddto.ServiceDTO;
import com.swapnil.service.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

//import static com.sun.beans.introspect.PropertyInfo.Name.required;

@RestController
@RequestMapping("/api/service-offering")
@RequiredArgsConstructor
public class ServiceOfferingController {

    private final ServiceOfferingService serviceOfferingService;

    @GetMapping("/saloon/{saloonId}")
    public ResponseEntity<Set<ServiceOffering>> getServicesBySaloonId(
            @PathVariable Long saloonId,
            @RequestParam(required = false) Long categoryId
    )throws Exception
    {

        Set<ServiceOffering> serviceOfferings = serviceOfferingService.getAllServiceBySaloonId(
                saloonId ,
                categoryId);

        return ResponseEntity.ok(serviceOfferings);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ServiceOffering> getServicesById(
            @PathVariable Long id
    ) throws Exception
    {
        ServiceOffering serviceOffering = serviceOfferingService.getServiceById(id);
        return ResponseEntity.ok(serviceOffering);
    }

    @GetMapping("/list/{ids}")
    public ResponseEntity<Set<ServiceOffering>> getServicesByIds(
            @PathVariable Set<Long> ids
    )throws Exception
    {

        Set<ServiceOffering> serviceOfferings = serviceOfferingService.getServicesByIds(
                ids);

        return ResponseEntity.ok(serviceOfferings);
    }
}
