package io.sookmyung.type.service;

import io.sookmyung.type.controller.dto.QuizDto;
import io.sookmyung.type.controller.dto.QuizResponseDto;
import io.sookmyung.type.repository.Note;
import io.sookmyung.type.repository.Quiz;
import io.sookmyung.type.repository.QuizRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizManagerImpl implements QuizManager {

    private final QuizRepository quizRepository;
    private final EntityManager em;

    @Override
    public List<QuizResponseDto> existQuiz(Long noteSeq) {
        List<Quiz> quizList = quizRepository.findAllByNote_NoteSeq(noteSeq);

        if (quizList.isEmpty()) return null;

        List<QuizResponseDto> quizDtoList = new ArrayList<>();
        for (Quiz quiz : quizList) {
            QuizResponseDto dto = new QuizResponseDto(quiz.getKeyword(), quiz.getContent());
            quizDtoList.add(dto);
        }
        return quizDtoList;
    }

    @Override
    @Transactional
    public List<QuizResponseDto> saveQuiz(Long noteSeq, List<QuizDto> generatedQuizList) {
        Note note = em.find(Note.class, noteSeq);
        List<QuizResponseDto> quizDtoList = new ArrayList<>();
        for (QuizDto quiz : generatedQuizList) {
            if (quiz.getQuiz() == null) continue;

            QuizResponseDto dto = new QuizResponseDto(quiz.getKeyword(), quiz.getQuiz());
            if (dto.getForward() == null | dto.getBackward() == null) continue;

            Quiz save = quizRepository.save(
                    Quiz.builder()
                            .keyword(quiz.getKeyword())
                            .content(quiz.getQuiz())
                            .noteSeq(noteSeq)
                            .note(note)
                            .build()
            );
            System.out.println("save = " + save);

            quizDtoList.add(dto);
        }

        return quizDtoList;
    }
}
