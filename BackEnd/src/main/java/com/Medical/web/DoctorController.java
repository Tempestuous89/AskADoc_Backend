package com.Medical.web;

import com.Medical.dao.requests.DoctorRegistrationRequest;
import com.Medical.security.security.JwtService;
import com.Medical.services.DoctorService;

import com.Medical.dao.entities.Doctor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("doctor")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;
    private final JwtService jwtService;

    @PostMapping("/registerDoctor")
    public ResponseEntity<Doctor> registerDoctor(
            @RequestHeader("Authorization") String authorizationHeader,
            @ModelAttribute DoctorRegistrationRequest request) throws IOException {

        // Extract the JWT token from the Authorization header
        String token = authorizationHeader.replace("Bearer ", "");

        // Extract user email from the token
        String userEmail = jwtService.extractUsername(token);

        // Register the doctor
        Doctor doctor = doctorService.registerDoctor(userEmail, request);

        return ResponseEntity.ok(doctor);
    }


}
