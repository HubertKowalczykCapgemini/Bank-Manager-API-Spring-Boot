package com.capgemni.bank_manager_api.service;

import com.capgemni.bank_manager_api.request.AuthenticationRequest;
import com.capgemni.bank_manager_api.request.RegisterRequest;
import com.capgemni.bank_manager_api.response.AuthenticationResponse;

public interface AuthenticationService {
    void register(RegisterRequest input) throws Exception;
    AuthenticationResponse login(AuthenticationRequest request) throws Exception;
}

