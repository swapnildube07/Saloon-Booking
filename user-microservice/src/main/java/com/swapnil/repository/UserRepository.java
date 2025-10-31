package com.swapnil.repository;

import com.swapnil.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {


    Long id(Long id);
}
