package io.sookmyung.type.service;

import io.sookmyung.type.repository.Note;
import io.sookmyung.type.repository.Slide;
import io.sookmyung.type.repository.SlideId;
import io.sookmyung.type.repository.SlideRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SlideManagerImpl implements SlideManager {
    private final SlideRepository slideRepository;
    private final SummaryService summaryService;
    private final SttManagerImpl sttService;
    private final EntityManager em;

    @Override
    @Transactional
    public String summarySlide(Long noteSeq, Integer slideIdx) {
        // 슬라이드 요약 조회
        SlideId id = SlideId.builder().noteSeq(noteSeq).slideIdx(slideIdx).build();
        Slide slide = slideRepository.findById(id).orElse(null);

        // 요약 존재하면 그대로 반환
        if (slide != null) return slide.getSummary();

        // 스크립트 긁어오기
        String original_text = sttService.findContentsBySlide(noteSeq, slideIdx);
        System.out.println("raw stt:> " + original_text);

        // 요약 실패
        Object o = summaryService.makeSummary(original_text);
        if (o == null) {
            System.out.println("failed to summarize slide");
            return null;
        }

        // 요약 저장
        String summary = o.toString()
                .replaceAll("[{}]", "")
                .replace("summary=", "");
        Note note = em.find(Note.class, noteSeq);

        Slide save = slideRepository.save(Slide.builder()
                .slideId(id)
                .summary(summary)
                .note(note)
                .build());

        // 요약 반환
        return save.getSummary();
    }

    @Override
    public String findSlideSummaries(Long noteSeq) {
        List<Slide> summaries = slideRepository.findAllByNote_NoteSeq(noteSeq);

        StringBuilder summary_text = new StringBuilder();
        for (Slide slide : summaries) {
            summary_text.append(slide.getSummary());
        }

        return summary_text.toString();
    }
}
