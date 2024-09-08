package com.Medical.services;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.Medical.dao.entities.Answer;
import com.Medical.dao.entities.Doctor;
import com.Medical.dao.entities.Patient;
import com.Medical.dao.entities.Question;
import com.Medical.dao.repositories.AnswerRepository;
import com.Medical.dao.repositories.PatientRepository;
import com.Medical.dao.repositories.QuestionRepository;
import com.Medical.dao.requests.AnswerRequest;
import com.Medical.dao.requests.QuestionRequest;
import com.Medical.security.user.User;
import com.Medical.security.user.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final PatientRepository patientRepository;  // Add this if not already present
    private final AnswerRepository answerRepository;  // Add this line

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

    @Override
    public Answer answerQuestion(String doctorEmail, AnswerRequest request) {
        User user = userRepository.findByEmail(doctorEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));

        if (!(user instanceof Doctor)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only doctors can answer questions");
        }

        Doctor doctor = (Doctor) user;

        Question question = questionRepository.findById(request.getQuestionId())
            .orElseThrow(() -> new RuntimeException("Question not found"));

        Answer answer = Answer.builder()
                .doctor(doctor)
                .question(question)
                .content(request.getContent())
                .createdDate(LocalDateTime.now())
                .build();

        return answerRepository.save(answer);
    }

    @Override
    public List<Question> getAllQuestions(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));

        if (user instanceof Patient) {
            Patient patient = (Patient) user;
            return questionRepository.findByPatient(patient);
        } else if (user instanceof Doctor) {
            return questionRepository.findAll();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user type");
        }
    }
}
