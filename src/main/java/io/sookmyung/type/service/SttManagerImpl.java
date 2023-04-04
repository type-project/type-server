package io.sookmyung.type.service;

import io.sookmyung.type.controller.dto.SttListDto;
import io.sookmyung.type.repository.Note;
import io.sookmyung.type.repository.Stt;
import io.sookmyung.type.repository.SttRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SttManagerImpl implements SttManager {

    private final SttRepository sttRepository;
    private final EntityManager entityManager;

    @Transactional
    @Override
    public String saveStt(Long noteSeq, Integer slideIdx, String content) {
        if (content == null) {
            return null;
        }

        Note note = entityManager.find(Note.class, noteSeq);
        System.out.println("note = " + note);

        Stt stt = Stt.builder()
                .noteSeq(noteSeq)
                .slideIdx(slideIdx)
                .content(content)
                .note(note)
                .build();

        Stt save = sttRepository.save(stt);

        return save.getContent();
    }

    @Override
    public List<SttListDto> findAllContent(Long noteSeq) {

        List<Stt> sttList = sttRepository.findAllByNote_NoteSeq(noteSeq);
        List<SttListDto> sttDtoList = new ArrayList<>();

        for (Stt stt : sttList) {
            SttListDto sttDto = new SttListDto(stt);
            sttDtoList.add(sttDto);
        }

        return sttDtoList;
    }

    @Override
    public String findContentsBySlide(Long noteSeq, Integer slideIdx) {
        List<Stt> sttList = sttRepository.findAllBySlideIdxAndNote_NoteSeq(slideIdx, noteSeq);
        StringBuilder original_text = new StringBuilder();

        for (Stt stt : sttList) {
            String content = stt.getContent();
            original_text.append(content);
        }

        return original_text.toString();
    }
}
