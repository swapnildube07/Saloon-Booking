package com.swapnil.service.impl;

import com.swapnil.modal.ServiceOffering;
import com.swapnil.payloaddto.CategoryDTO;
import com.swapnil.payloaddto.SaloonDTO;
import com.swapnil.payloaddto.ServiceDTO;
import com.swapnil.repositary.ServiceOfferingRepositary;
import com.swapnil.service.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceOfferingServiceImpl implements ServiceOfferingService {

    private final ServiceOfferingRepositary serviceOfferingRepositary;

    @Override
    public ServiceOffering createService(SaloonDTO saloonDTO,
                                                 CategoryDTO categoryDTO,
                                                 ServiceDTO serviceDTO) {
        ServiceOffering serviceOffering = new ServiceOffering();
        serviceOffering.setImage(serviceDTO.getImage());
        serviceOffering.setSaloonId(saloonDTO.getId());
        serviceOffering.setName(serviceDTO.getName());
        serviceOffering.setDescription(serviceDTO.getDescription());
        serviceOffering.setPrice(serviceDTO.getPrice());
        serviceOffering.setDuration(serviceDTO.getDuration());
        serviceOffering.setCategoryId(categoryDTO.getId());
        return serviceOfferingRepositary.save(serviceOffering);
    }

    @Override
    public ServiceOffering updateService(Long serviceId,
                                         ServiceOffering updatedService) throws Exception {
        ServiceOffering existingService = serviceOfferingRepositary.findById(serviceId)
                .orElseThrow(() -> new Exception("Service does not exist with ID: " + serviceId));

        existingService.setImage(updatedService.getImage());
        existingService.setName(updatedService.getName());
        existingService.setDescription(updatedService.getDescription());
        existingService.setPrice(updatedService.getPrice());
        existingService.setDuration(updatedService.getDuration());

        return serviceOfferingRepositary.save(existingService);
    }

    @Override
    public Set<ServiceOffering> getAllServiceBySaloonId(Long saloonId, Long categoryId) {
        Set<ServiceOffering> services = serviceOfferingRepositary.findBySaloonId(saloonId);

        if (categoryId != null) {
            services = services.stream()
                    .filter(service -> service.getCategoryId() != null && service.getCategoryId().equals(categoryId))
                    .collect(Collectors.toSet());
        }
        return services;
    }

    @Override
    public Set<ServiceOffering> getServicesByIds(Set<Long> ids) {
        List<ServiceOffering> services = serviceOfferingRepositary.findAllById(ids);
        return new HashSet<>(services);
    }

    @Override
    public ServiceOffering getServiceById(Long id) throws Exception {
        return serviceOfferingRepositary.findById(id)
                .orElseThrow(() -> new Exception("Service does not exist with ID: " + id));
    }
}
