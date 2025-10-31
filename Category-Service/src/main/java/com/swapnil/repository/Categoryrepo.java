package com.swapnil.repository;

import java.util.Set;

import com.swapnil.modal.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Categoryrepo extends JpaRepository<Category, Long> {


    Set<Category> findBySaloonId(Long saloonId);


}
