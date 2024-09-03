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

        // Convert MultipartFile to byte[]
        byte[] profileImageBytes = request.getProfileImage() != null ? request.getProfileImage().getBytes() : null;

        // Create a Doctor entity
        Doctor doctor = Doctor.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dateOfBirth(user.getDateOfBirth())
                .email(user.getEmail())
                .password(user.getPassword())
                .city(user.getCity())
                .gender(user.getGender())
                .roles(user.getRoles())
                .accountLocked(user.isAccountLocked())
                .enabled(user.isEnabled())
                .createdDate(user.getCreatedDate())
                .lastModifiedDate(user.getLastModifiedDate())
                .speciality(request.getSpeciality())
                .education(request.getEducation())
                .workPlace(request.getWorkPlace())
                .position(request.getPosition())
                .workExperienceYears(request.getWorkExperienceYears())
                .awards(request.getAwards())
                .contactPhone(request.getContactPhone())
                .contactEmail(request.getContactEmail())
                .aboutMe(request.getAboutMe())
                .specializationDetails(request.getSpecializationDetails())
                .workExperienceDetails(request.getWorkExperienceDetails())
                .furtherTraining(request.getFurtherTraining())
                .achievementsAndAwards(request.getAchievementsAndAwards())
                .scientificWorks(request.getScientificWorks())
                .profileImage(profileImageBytes)
                .build();

        // Set Doctor reference in Certificates
        List<Certificate> certificateList = request.getCertificates() != null ?
                request.getCertificates().stream().map(file -> {
                    try {
                        Certificate cert = Certificate.builder()
                                .certificateName(file.getOriginalFilename())
                                .certificateFile(file.getBytes())
                                .doctor(doctor) // Set the doctor reference
                                .build();
                        return cert;
                    } catch (IOException e) {
                        throw new RuntimeException("Error processing certificate file", e);
                    }
                }).collect(Collectors.toList()) : Collections.emptyList();

        doctor.setCertificates(certificateList); // Set certificates in doctor

        // Save the Doctor entity
        return doctorRepository.save(doctor);
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
}
