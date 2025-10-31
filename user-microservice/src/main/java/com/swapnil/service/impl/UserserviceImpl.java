package com.swapnil.service.impl;

import com.swapnil.exception.UserException;
import com.swapnil.modal.User;
import com.swapnil.repository.UserRepository;
import com.swapnil.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserserviceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public User crateUser(User user) {
        return userRepository.save(user);
    }

    @SneakyThrows
    @Override
    public ResponseEntity<User> getUserById(Long id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new UserException("User Not Found With ID: " + id));
    }


    @Override
    public User updateUser(User user) throws UserException {
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new UserException("User Not Found With This ID"));

        existingUser.setFullName(user.getFullName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setPhoneNumber(user.getPhoneNumber());

        return userRepository.save(existingUser);
    }


    @Override
    public void deleteUser(Long id) throws UserException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserException("User Not Found With This ID"));

        userRepository.deleteById(user.getId());
    }


    @Override
    public User getUserByEmail(String email) {
        return null;
    }


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
