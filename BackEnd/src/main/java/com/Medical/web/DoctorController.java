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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("doctor")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;
    private final JwtService jwtService;

    @PostMapping(value = "/verifyDoctor", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Doctor> verifyDoctor(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestPart("data") @Valid DoctorVerificationRequest request,
            @RequestPart(value = "certificates", required = false) List<MultipartFile> certificates) throws IOException {

        // Extract the JWT token from the Authorization header
        String token = authorizationHeader.replace("Bearer ", "");

        // Extract user email from the token
        String userEmail = jwtService.extractUsername(token);

        // Add certificates to the request
        request.setCertificates(certificates);

        // Verify the doctor
        Doctor doctor = doctorService.verifyDoctor(userEmail, request);

        return ResponseEntity.ok(doctor);
    }

    @PutMapping("updateData")
    public ResponseEntity<Doctor> updateDoctorData(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody DoctorUpdateDataRequest request) throws IOException {   

        String token = authorizationHeader.replace("Bearer ", "");
        String userEmail = jwtService.extractUsername(token);
        Doctor doctor = doctorService.getDoctorByEmail(userEmail).orElse(null);
        if (doctor == null) {
            return ResponseEntity.notFound().build();
        }
        Doctor updatedDoctor = doctorService.updateDoctorData(doctor.getId(), request);
        return ResponseEntity.ok(updatedDoctor);
    }

}
