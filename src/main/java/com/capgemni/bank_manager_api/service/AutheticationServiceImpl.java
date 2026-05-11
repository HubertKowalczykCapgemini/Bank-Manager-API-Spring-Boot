package com.capgemni.bank_manager_api.service;

import com.capgemni.bank_manager_api.entity.Authority;
import com.capgemni.bank_manager_api.entity.User;
import com.capgemni.bank_manager_api.repository.UserRepository;
import com.capgemni.bank_manager_api.request.AuthenticationRequest;
import com.capgemni.bank_manager_api.request.RegisterRequest;
import com.capgemni.bank_manager_api.response.AuthenticationResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.ldap.PagedResultsControl;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service
public class AutheticationServiceImpl implements AuthenticationService{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private  final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    public AutheticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    @Transactional
    public void register(RegisterRequest input) throws Exception {
        if(isEmailTaken(input.getEmail())) {
            throw new IllegalArgumentException("Email is already taken");
        }

        User user = buildNewUser(input);
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthenticationResponse login(AuthenticationRequest request) throws Exception {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword()));

        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        String jwtToken = jwtService.generateToken(new HashMap<>(), user);

        return new AuthenticationResponse(jwtToken);


    }

    private User buildNewUser(RegisterRequest input) {
        User user = new User();
        user.setId(null);
        user.setFirstName(input.getFirstName());
        user.setLastName(input.getLastName());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setBalance(input.getInitialBalance());
        user.setAuthorities(initialAuthority());
        return user;
    }

    private List<Authority> initialAuthority() {
        boolean isFirstUser = userRepository.count() == 0;
        List<Authority> authorities = new ArrayList<>();

        authorities.add(new Authority("ROLE_USER"));
        if(isFirstUser){
            authorities.add(new Authority("ROLE_ADMIN"));
        }

        return authorities;
    }


    private boolean isEmailTaken(String email){
        return userRepository.findByEmail(email).isPresent();
    }
}
