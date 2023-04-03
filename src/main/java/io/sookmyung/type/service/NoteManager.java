package io.sookmyung.type.service;

import io.sookmyung.type.controller.dto.NoteListResponseDto;
import io.sookmyung.type.controller.dto.NoteResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface NoteManager {
    // Todo 노트 생성
    Long saveNote(Long userSeq, String name, String slideUrl, MultipartFile thumbnail);

    // Todo 노트 이름 수정
    // Todo 유저의 모든 노트 조회
    List<NoteListResponseDto> findAllNotes(Long userSeq);

    // Todo 노트 조회 (이름, 그동안의 스크립트, pdf)
    NoteResponseDto findNote(Long noteSeq);

    // Todo 노트 요약 조회/요청/저장
    String summaryNote(Long noteSeq);
}
