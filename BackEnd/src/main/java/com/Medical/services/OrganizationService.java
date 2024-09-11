package com.Medical.services;



import com.Medical.dao.entities.Organization;
import com.Medical.dao.requests.OrganizationVerificationRequest;


import java.util.List;
import java.util.Optional;

public interface OrganizationService {
    Organization saveOrUpdateOrganization(Organization organization);
    List<Organization> getAllOrganization();
    Optional<Organization> getOrganizationById(Integer id);
    void deleteOrganization(Integer id);
    Organization verifyOrganization(String userEmail, OrganizationVerificationRequest request);
    Optional<Organization> getOrganizationProfile(String email);
}
