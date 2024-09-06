package com.Medical.services;


import com.Medical.dao.entities.Organization;
import com.Medical.dao.requests.OrganizationUpdateDataRequest;
import com.Medical.dao.requests.OrganizationVerificationRequest;
import com.Medical.dao.repositories.OrganizationRepository;
import com.Medical.security.user.User;
import com.Medical.security.user.UserRepository;

import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;


    @Override
    public Organization saveOrUpdateOrganization(Organization organization) {
        return organizationRepository.save(organization);
    }

    @Override
    public List<Organization> getAllOrganization() {
        return organizationRepository.findAll();
    }

    @Override
    public Optional<Organization> getOrganizationById(Integer id) {
        return organizationRepository.findById(id);
    }

    @Override
    public void deleteOrganization(Integer id) {
        organizationRepository.deleteById(id);
    }

    @Override
    public Organization verifyOrganization(String userEmail, OrganizationVerificationRequest request) {
        // Find the User by email
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create and populate the Organization entity
        Organization organization = Organization.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dateOfBirth(user.getDateOfBirth())
                .email(user.getEmail())
                .password(user.getPassword())
                .city(user.getCity())
                .gender(user.getGender())
                .roles(user.getRoles())
                .accountLocked(user.isAccountNonLocked())
                .enabled(user.isEnabled())
                .verified(true)
                .createdDate(user.getCreatedDate())
                .lastModifiedDate(user.getLastModifiedDate())
                //From the JSON REQUEST
                .organizationName(request.getOrganizationName())
                .typeOfInstitution(request.getTypeOfInstitution())
                .description(request.getDescription())
                .facilityCity(request.getFacilityCity())
                .facilityAddress(request.getFacilityAddress())
                .phoneNumber(request.getPhoneNumber())
                .schedule(request.getSchedule())
                .website(request.getWebsite())
                .facilityEmailAddress(request.getFacilityEmailAddress())
                .build();

        // Save the Organization entity
        return organizationRepository.save(organization);
    }

    @Transactional
    @Override
    public Organization updateOrganizationData(Integer id, OrganizationUpdateDataRequest request) throws IOException {
        Organization organization = organizationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Organization not found"));
        
        updateOrganizationFields(organization, request);
        return organizationRepository.save(organization);
    }

    private void updateOrganizationFields(Organization organization, OrganizationUpdateDataRequest request) {
        organization.setOrganizationName(request.getOrganizationName());
        organization.setTypeOfInstitution(request.getTypeOfInstitution());
        organization.setDescription(request.getDescription());
        organization.setFacilityCity(request.getFacilityCity());
        organization.setFacilityAddress(request.getFacilityAddress());
        organization.setPhoneNumber(request.getPhoneNumber());
        organization.setSchedule(request.getSchedule());
        organization.setWebsite(request.getWebsite());
        organization.setFacilityEmailAddress(request.getFacilityEmailAddress());
    }

    @Override
    public Optional<Organization> getOrganizationByEmail(String email) {
        return organizationRepository.findByEmail(email);
    }
}

