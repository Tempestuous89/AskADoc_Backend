package com.Medical.services;

import com.Medical.dao.entities.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientService {
    Patient saveOrUpdatePatient(Patient patient);
    List<Patient> getAllPatients();
    Optional<Patient> getPatientById(Integer id);
    void deletePatientById(Integer id);
    Patient findByEmail(String email);
}