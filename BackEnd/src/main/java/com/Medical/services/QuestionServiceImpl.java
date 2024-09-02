package com.Medical.services;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.Medical.dao.entities.Patient;
import com.Medical.dao.entities.Question;
import com.Medical.dao.repositories.PatientRepository;
import com.Medical.dao.repositories.QuestionRepository;
import com.Medical.dao.requests.QuestionRequest;
import com.Medical.security.user.User;
import com.Medical.security.user.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final PatientRepository patientRepository;  // Add this if not already present

    @Override
    public Question askAQuestion(String userEmail, QuestionRequest request) {

        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));

        if (!(user instanceof Patient)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only patients can ask questions");
        }

        Patient patient = (Patient) user;

        Question question = Question.builder()
                .patient(patient)
                .category(request.getCategory())
                .title(request.getTitle())
                .description(request.getDescription())
                .keyword(request.getKeyword())
                .createdDate(LocalDateTime.now())
                .build();

        return questionRepository.save(question);
    }
}
