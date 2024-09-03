package com.Medical.web;

import com.Medical.dao.requests.DoctorUpdateDataRequest;
import com.Medical.dao.requests.DoctorVerificationRequest;
import com.Medical.security.security.JwtService;
import com.Medical.services.DoctorService;

import jakarta.validation.Valid;

import org.springframework.http.MediaType;

import com.Medical.dao.entities.Doctor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("doctor")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;
    private final JwtService jwtService;

    @PostMapping("/verifyDoctor")
    public ResponseEntity<Doctor> verifyDoctor(
            @RequestHeader("Authorization") String authorizationHeader,
            @ModelAttribute DoctorVerificationRequest request) throws IOException {

        // Extract the JWT token from the Authorization header
        String token = authorizationHeader.replace("Bearer ", "");

        // Extract user email from the token
        String userEmail = jwtService.extractUsername(token);

        // Register the doctor
        Doctor doctor = doctorService.verifyDoctor(userEmail, request);

        return ResponseEntity.ok(doctor);
    }

    @PutMapping("updateData")
    public ResponseEntity<Doctor> updateDoctorData(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody DoctorUpdateDataRequest request) throws IOException {   

        String token = authorizationHeader.replace("Bearer ", "");
        String userEmail = jwtService.extractUsername(token);
        Doctor updatedDoctor = doctorService.updateDoctorData(userEmail, request);
        return ResponseEntity.ok(updatedDoctor);
    }

}
