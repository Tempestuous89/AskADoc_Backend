package com.Medical.services;



import com.Medical.dao.entities.Organization;
import com.Medical.dao.requests.OrganizationUpdateDataRequest;
import com.Medical.dao.requests.OrganizationVerificationRequest;

import io.jsonwebtoken.io.IOException;

import java.util.List;
import java.util.Optional;

public interface OrganizationService {
    Organization saveOrUpdateOrganization(Organization organization);
    List<Organization> getAllOrganization();
    Optional<Organization> getOrganizationById(Integer id);
    void deleteOrganization(Integer id);
    Organization verifyOrganization(String userEmail, OrganizationVerificationRequest request);
    Organization updateOrganizationData(Integer id, OrganizationUpdateDataRequest request) throws IOException;
    Optional<Organization> getOrganizationByEmail(String email);
}
