package com.swapnil.service;

import com.swapnil.exception.UserException;
import com.swapnil.modal.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    User crateUser(User user);
    ResponseEntity<User> getUserById(Long id);
    User updateUser(User user) throws UserException;
    void deleteUser(Long id) throws UserException;
    User getUserByEmail(String email);
    List<User> getAllUsers();
}
