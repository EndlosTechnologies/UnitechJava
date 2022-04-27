package com.unitechApi.user.controller;

import com.unitechApi.Payload.response.PageResponse;
import com.unitechApi.user.Repository.PassWordRepository;
import com.unitechApi.user.model.PasswordEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/unitech/api/v1/user")
public class PasswordController {
    private final PassWordRepository passWordRepository;
    private final PasswordEncoder encoder;

    public PasswordController(PassWordRepository passWordRepository, PasswordEncoder encoder) {
        this.passWordRepository = passWordRepository;
        this.encoder = encoder;
    }
    @RequestMapping(value = "/password", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HR') or hasRole('ROLE_SUBADMIN')")
    public ResponseEntity<?> SavePassWord(@RequestBody PasswordEntity password) {

        password.setPassword(encoder.encode(password.getPassword()));
        PasswordEntity passwordEntitya = passWordRepository.save(password);
        return new ResponseEntity<>(PageResponse.SuccessResponse(passwordEntitya), HttpStatus.CREATED);
    }
}
