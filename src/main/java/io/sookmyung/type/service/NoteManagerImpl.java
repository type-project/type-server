package io.sookmyung.type.service;

import io.sookmyung.type.controller.dto.NoteListResponseDto;
import io.sookmyung.type.controller.dto.NoteResponseDto;
import io.sookmyung.type.repository.Note;
import io.sookmyung.type.repository.NoteRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class NoteManagerImpl implements NoteManager {
    private final NoteRepository noteRepository;
    private final SlideManagerImpl slideService;
    private final SummaryService summaryService;
    private final S3FileUploader s3FileUploader;

    private final EntityManager em;

    @Override
    @Transactional
    public Long saveNote(Long userSeq, String name, String slideUrl, MultipartFile thumbnail) {
        // 표지 이미지 썸네일 크기로 변환 후 버킷에 업로드
        String thumbnailUrl;

        try {
            thumbnailUrl = s3FileUploader.uploadFileToS3(thumbnail);
            System.out.println("thumbnailUrl = " + thumbnailUrl);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 노트 저장
        Note note = Note.builder().userSeq(userSeq)
                .name(name)
                .slideUrl(slideUrl)
                .thumbnail(thumbnailUrl)
                .build();

        Note save = noteRepository.save(note);

        return save.getNoteSeq();
    }

    @Override
    public List<NoteListResponseDto> findAllNotes(Long userSeq) {
        List<Note> notes = noteRepository.findNotesByUserSeq(userSeq);

        List<NoteListResponseDto> noteDtoList = new ArrayList<>();
        for (Note note : notes) {
            NoteListResponseDto dto = new NoteListResponseDto(note);
            noteDtoList.add(dto);
        }
        return noteDtoList;
    }

    @Override
    public NoteResponseDto findNote(Long noteSeq) {
        Note note = noteRepository.findById(noteSeq)
                .orElseThrow(() -> new NoSuchElementException("해당 노트 정보가 존재하지 않습니다."));

        return new NoteResponseDto(note);
    }

    @Override
    @Transactional
    public String summaryNote(Long noteSeq) {
        // 이미 있는지 확인
        Note note = noteRepository.findById(noteSeq).orElse(null);
        System.out.println("note:>" + note);

        // 노트 없음
        if (note == null) {
            System.out.println("존재하지 않는 노트입니다.");
            return null;
        }
        // 이미 요약 있음
        if (note.getSummary() != null) return note.getSummary();

        // 슬라이드 요약 긁어오기
        String slide_text = slideService.findSlideSummaries(noteSeq);
        System.out.println(slide_text);

        // 요약 실패
        Object o = summaryService.makeSummary(slide_text);
        if (o == null) {
            System.out.println("failed to summarize note");
            return null;
        }
        System.out.println(o);

        // 요약 저장
        String summary = o.toString()
                .replaceAll("[{}]", "")
                .replace("summary=", "");
        System.out.println(summary);
        note.setSummary(summary);

        Optional<Note> byId = noteRepository.findById(noteSeq);
        System.out.println("byId = " + byId);

        // 요약 반환
        return note.getSummary();
    }
}
