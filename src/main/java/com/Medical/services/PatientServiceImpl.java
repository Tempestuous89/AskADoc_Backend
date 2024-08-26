package com.Medical.services;

import com.Medical.dao.entities.Patient;
import com.Medical.dao.repositories.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    /*private final UserRepository userRepository;*/

    @Override
    public Patient saveOrUpdatePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public Optional<Patient> getPatientById(Integer id) {
        return patientRepository.findById(id);
    }

    @Override
    public void deletePatientById(Integer id) {
        patientRepository.deleteById(id);
    }

}
