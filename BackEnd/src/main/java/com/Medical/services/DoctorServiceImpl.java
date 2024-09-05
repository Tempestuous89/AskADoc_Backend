package com.Medical.services;


import com.Medical.dao.entities.Certificate;
import com.Medical.dao.entities.Doctor;
import com.Medical.dao.entities.Patient;
import com.Medical.dao.repositories.DoctorRepository;
import com.Medical.dao.requests.DoctorUpdateDataRequest;
import com.Medical.dao.requests.DoctorVerificationRequest;
import com.Medical.security.user.User;
import com.Medical.security.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;

    @Override
    public Doctor saveOrUpdateDoctor(Doctor doctor){
        return doctorRepository.save(doctor);
    }

    @Override
    public List<Doctor> getAllDoctors(){
        return doctorRepository.findAll();
    }

    @Override
    public Optional<Doctor> getDoctorById(Integer id){
        return doctorRepository.findById(id);
    }

    @Override
    public void deleteDoctorById(Integer id){
        doctorRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Doctor verifyDoctor(String userEmail, DoctorVerificationRequest request) throws IOException {
        // Find the User by email
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user instanceof Doctor) {
            Doctor doctor = (Doctor) user;

            // Update doctor fields
            doctor.setSpeciality(request.getSpeciality());
            doctor.setEducation(request.getEducation());
            doctor.setWorkPlace(request.getWorkPlace());
            doctor.setPosition(request.getPosition());
            doctor.setWorkExperienceYears(request.getWorkExperienceYears());
            doctor.setAwards(request.getAwards());
            doctor.setContactPhone(request.getContactPhone());
            doctor.setContactEmail(request.getContactEmail());
            doctor.setAboutMe(request.getAboutMe());
            doctor.setSpecializationDetails(request.getSpecializationDetails());
            doctor.setWorkExperienceDetails(request.getWorkExperienceDetails());
            doctor.setFurtherTraining(request.getFurtherTraining());
            doctor.setAchievementsAndAwards(request.getAchievementsAndAwards());
            doctor.setScientificWorks(request.getScientificWorks());
            doctor.setVerified(true);

            // Handle certificates
            if (request.getCertificates() != null && !request.getCertificates().isEmpty()) {
                List<Certificate> certificateList = request.getCertificates().stream()
                    .map(file -> {
                        try {
                            return Certificate.builder()
                                    .certificateName(file.getOriginalFilename())
                                    .certificateFile(file.getBytes())
                                    .doctor(doctor)
                                    .build();
                        } catch (IOException e) {
                            throw new RuntimeException("Error processing certificate file", e);
                        }
                    })
                    .collect(Collectors.toList());

                doctor.setCertificates(certificateList);
            }

            // Save the updated Doctor entity
            return doctorRepository.save(doctor);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not a Doctor");
        }
    }

    @Transactional
    @Override
    public Doctor updateDoctorData(String userEmail, DoctorUpdateDataRequest request) throws IOException {
        // Find the User by email
        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));

        if (!(user instanceof Doctor)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only Doctors can update this form");
        }

        Doctor doctor = (Doctor) user;

        doctor.setFirstName(request.getFirstName());
        doctor.setLastName(request.getLastName());  
        doctor.setDateOfBirth(request.getDateOfBirth());
        doctor.setGender(request.getGender());
        doctor.setCity(request.getCity());
        doctor.setEmail(request.getEmail());
        doctor.setSpeciality(request.getSpeciality());
        doctor.setEducation(request.getEducation());
        doctor.setWorkPlace(request.getWorkPlace());
        doctor.setPosition(request.getPosition());
        doctor.setWorkExperienceYears(request.getWorkExperienceYears());
        doctor.setAwards(request.getAwards());
        doctor.setContactPhone(request.getContactPhone());
        doctor.setContactEmail(request.getContactEmail());
        doctor.setAboutMe(request.getAboutMe());
        doctor.setSpecializationDetails(request.getSpecializationDetails());
        doctor.setWorkExperienceDetails(request.getWorkExperienceDetails());
        doctor.setFurtherTraining(request.getFurtherTraining());
        doctor.setAchievementsAndAwards(request.getAchievementsAndAwards());
        doctor.setScientificWorks(request.getScientificWorks());

        return doctorRepository.save(doctor);
    }

    @Transactional
    @Override
    public Doctor updateDoctorData(Integer id, DoctorUpdateDataRequest request) throws IOException {
        Doctor doctor = doctorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Doctor not found"));
        
        updateDoctorFields(doctor, request);
        return doctorRepository.save(doctor);
    }

    private void updateDoctorFields(Doctor doctor, DoctorUpdateDataRequest request) {
        doctor.setFirstName(request.getFirstName());
        doctor.setLastName(request.getLastName());
        doctor.setDateOfBirth(request.getDateOfBirth());
        doctor.setGender(request.getGender());
        doctor.setCity(request.getCity());
        doctor.setEmail(request.getEmail());
        doctor.setSpeciality(request.getSpeciality());
        doctor.setEducation(request.getEducation());
        doctor.setWorkPlace(request.getWorkPlace());
        doctor.setPosition(request.getPosition());
        doctor.setWorkExperienceYears(request.getWorkExperienceYears());
        doctor.setAwards(request.getAwards());
        doctor.setContactPhone(request.getContactPhone());
        doctor.setContactEmail(request.getContactEmail());
        doctor.setAboutMe(request.getAboutMe());
        doctor.setSpecializationDetails(request.getSpecializationDetails());
        doctor.setWorkExperienceDetails(request.getWorkExperienceDetails());
        doctor.setFurtherTraining(request.getFurtherTraining());
        doctor.setAchievementsAndAwards(request.getAchievementsAndAwards());
        doctor.setScientificWorks(request.getScientificWorks());
    }

    @Override
    public Optional<Doctor> getDoctorByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email)
                .filter(user -> user instanceof Doctor)
                .map(user -> (Doctor) user)
                .orElse(null));
    }
}
