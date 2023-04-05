package io.sookmyung.type.service;

import io.sookmyung.type.controller.dto.QuizDto;
import io.sookmyung.type.controller.dto.QuizResponseDto;

import java.util.List;

public interface QuizManager {

    List<QuizResponseDto> existQuiz(Long noteSeq);

    List<QuizResponseDto> saveQuiz(Long noteSeq, List<QuizDto> generatedQuizList);
}
