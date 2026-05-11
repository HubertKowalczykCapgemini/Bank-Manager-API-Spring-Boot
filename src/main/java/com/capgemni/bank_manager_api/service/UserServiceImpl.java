package com.capgemni.bank_manager_api.service;

import com.capgemni.bank_manager_api.entity.Authority;
import com.capgemni.bank_manager_api.entity.User;
import com.capgemni.bank_manager_api.repository.UserRepository;
import com.capgemni.bank_manager_api.response.UserResponse;
import com.capgemni.bank_manager_api.util.FindAuthenticateUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.StreamSupport;


@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final FindAuthenticateUser findAuthenticateUser;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, FindAuthenticateUser findAuthenticateUser, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.findAuthenticateUser = findAuthenticateUser;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserInfo() {
        User user = findAuthenticateUser.getAuthenticatedUser();
        return new UserResponse(user.getId(),
            user.getFirstName() + " " + user.getLastName(),
            user.getEmail(),
            user.getBalance(),
            user.getAuthorities().stream().map(auth -> (Authority) auth).toList()
        );
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
            .map(this::convertToUserResponse)
            .toList();
    }

    @Override
    public UserResponse getUserById(Long id) {
        User selectedUser = userRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Not Found"));

        return convertToUserResponse(selectedUser);
    }

    @Override
    @Transactional

    public void deleteUserById(Long id) {
        User selectedUser = userRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Not Found"));
        userRepository.delete(selectedUser);
    }

    private UserResponse convertToUserResponse(User user) {

        return new UserResponse(
            user.getId(),
            user.getFirstName() + " " + user.getLastName(),
            user.getEmail(),
            user.getBalance(),
            user.getAuthorities().stream().map(auth -> (Authority) auth).toList());
    }
}
