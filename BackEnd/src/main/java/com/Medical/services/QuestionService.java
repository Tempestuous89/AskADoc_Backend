package com.Medical.services;

import com.Medical.dao.entities.Question;
import com.Medical.dao.requests.QuestionRequest;

public interface QuestionService {

    Question askAQuestion(String userEmail, QuestionRequest request);
}
