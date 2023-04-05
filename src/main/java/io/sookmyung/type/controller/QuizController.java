package io.sookmyung.type.controller;

import io.sookmyung.type.controller.dto.QuizDto;
import io.sookmyung.type.controller.dto.QuizResponseDto;
import io.sookmyung.type.service.QuizManagerImpl;
import io.sookmyung.type.service.QuizService;
import io.sookmyung.type.service.SlideManagerImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("quiz")
public class QuizController {
    @Autowired
    private SlideManagerImpl slideManager;
    @Autowired
    private QuizService quizService;
    @Autowired
    private QuizManagerImpl quizManager;

    @GetMapping
    public ResponseEntity<List<QuizResponseDto>> quiz(@RequestParam("noteSeq") Long noteSeq) {
        log.info("/quiz");

        // 이미 퀴즈 존재
        List<QuizResponseDto> quizList = quizManager.existQuiz(noteSeq);
        if (quizList != null) return ResponseEntity.ok(quizList);

        // 퀴즈 새로 만들기
        String slideSummaries = slideManager.findSlideSummaries(noteSeq);
        List<QuizDto> generatedQuizList = quizService.generateQuiz(slideSummaries);
        quizList = quizManager.saveQuiz(noteSeq, generatedQuizList);
        return ResponseEntity.ok(quizList);
    }
}
