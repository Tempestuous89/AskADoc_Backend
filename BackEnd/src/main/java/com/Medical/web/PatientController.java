package com.Medical.web;

import com.Medical.dao.entities.Patient;
import com.Medical.dao.entities.Question;
import com.Medical.dao.requests.QuestionRequest;
import com.Medical.security.security.JwtService;
import com.Medical.services.PatientService;
import com.Medical.services.QuestionService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("patient")
@RequiredArgsConstructor
public class PatientController {

    private final QuestionService questionService;
    private final JwtService jwtService;

    @PostMapping("/askAQuestion")
    public ResponseEntity<Question> askAQuestion(@RequestHeader("Authorization") String authorizationHeader,
                                                @RequestBody QuestionRequest request) {

        // Extract the JWT token from the Authorization header
        String token = authorizationHeader.replace("Bearer ", "");

        // Extract user email from the token
        String userEmail = jwtService.extractUsername(token);
                                                    
        // Ask a question
        Question question = questionService.askAQuestion(userEmail, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(question);
    }

}
