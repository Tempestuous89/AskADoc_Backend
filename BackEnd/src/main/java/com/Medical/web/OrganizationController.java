package com.Medical.web;


import com.Medical.dao.requests.OrganizationVerificationRequest;
import com.Medical.security.security.JwtService;
import com.Medical.services.OrganizationService;

import jakarta.validation.Valid;

import com.Medical.dao.entities.Organization;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("organization")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;
    private final JwtService jwtService;

    @PostMapping("/verifyOrganization")
    public ResponseEntity<Organization> verifyOrganization(
            @RequestHeader("Authorization") String authorizationHeader,
            @Valid @RequestBody OrganizationVerificationRequest request) {

        // Extract the JWT token from the Authorization header
        String token = authorizationHeader.replace("Bearer ", "");

        // Extract user email from the token
        String userEmail = jwtService.extractUsername(token);

        // Register the organization
        Organization organization = organizationService.verifyOrganization(userEmail, request);

        return ResponseEntity.ok(organization);
    }
}

