package com.Medical.services;



import com.Medical.dao.entities.Doctor;
import com.Medical.dao.requests.DoctorRegistrationRequest;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface DoctorService {
    Doctor saveOrUpdateDoctor(Doctor doctor);
    List<Doctor> getAllDoctors();
    Optional<Doctor> getDoctorById(Integer id);
    void deleteDoctorById(Integer id);

    Doctor registerDoctor(String userEmail, DoctorRegistrationRequest request) throws IOException;
}
