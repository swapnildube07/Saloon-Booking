package com.swapnil.repositary;

import com.swapnil.modal.ServiceOffering;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface ServiceOfferingRepositary extends JpaRepository<ServiceOffering, Long> {

    Set<ServiceOffering> findBySaloonId(Long saloonId);
    

}
