package com.swapnil.service;

import com.swapnil.modal.ServiceOffering;
import com.swapnil.payloaddto.CategoryDTO;
import com.swapnil.payloaddto.SaloonDTO;
import com.swapnil.payloaddto.ServiceDTO;

import java.util.Set;

public interface ServiceOfferingService {
    ServiceOffering createService(SaloonDTO saloonDTO ,
                                          CategoryDTO categoryDTO ,
                                          ServiceDTO serviceDTO );

    ServiceOffering updateService(Long serviceId, ServiceOffering serviceOffering) throws Exception;

    Set<ServiceOffering> getAllServiceBySaloonId(Long saloonId ,Long categoryId);

    Set<ServiceOffering> getServicesByIds(Set<Long> ids);

    ServiceOffering getServiceById(Long id) throws Exception; // Fixed return type
}
