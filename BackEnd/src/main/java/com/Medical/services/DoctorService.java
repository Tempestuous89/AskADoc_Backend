package com.Medical.services;



import com.Medical.dao.entities.Doctor;
import com.Medical.dao.requests.DoctorVerificationRequest;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface DoctorService {
    Doctor saveOrUpdateDoctor(Doctor doctor);
    List<Doctor> getAllDoctors();
    Optional<Doctor> getDoctorById(Integer id);
    void deleteDoctorById(Integer id);

    Doctor verifyDoctor(String userEmail, DoctorVerificationRequest request) throws IOException;
}
