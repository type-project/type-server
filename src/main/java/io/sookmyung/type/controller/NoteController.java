package io.sookmyung.type.controller;

import io.sookmyung.type.controller.dto.NoteListResponseDto;
import io.sookmyung.type.controller.dto.NoteResponseDto;
import io.sookmyung.type.controller.dto.SttListDto;
import io.sookmyung.type.service.NoteManagerImpl;
import io.sookmyung.type.service.SttManagerImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private NoteManagerImpl noteService;
    @Autowired
    private SttManagerImpl sttService;

    @PostMapping(value = "/init", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Long> initNote(@RequestParam("name") String noteName,
                                         @RequestParam("slideUrl") String slideUrl,
                                         @RequestParam("thumbnail") MultipartFile thumbnail) throws IOException {
        // 노트 이름, 슬라이드 위치(이건 기기에 저장하는 게 나은가) 받아서 저장하고 번호 반환. 번호가 없다면 실패한 것.
        log.info("/note/init");

        Long userSeq = 1L;
        Long noteSeq = noteService.saveNote(userSeq, noteName, slideUrl, thumbnail);
        return ResponseEntity.ok().body(noteSeq);
    }

    @GetMapping(value = "/list")
    public ResponseEntity<List<NoteListResponseDto>> listNotes() {
        // 노트 번호, 이름, 썸네일
        log.info("/note/list");

        Long userSeq = 1L;
        List<NoteListResponseDto> allNotes = noteService.findAllNotes(userSeq);
        return ResponseEntity.ok().body(allNotes);
    }

    @GetMapping()
    public ResponseEntity<NoteResponseDto> getNote(@RequestParam("noteSeq") Long noteSeq) {
        // 노트 번호, 노트 이름, 슬라이드 위치, 존재하는 자막 리스트
        log.info("/note?noteSeq=" + noteSeq);

        NoteResponseDto note = noteService.findNote(noteSeq);
        List<SttListDto> allContent = sttService.findAllContent(noteSeq);
        note.sttList(allContent);

        return ResponseEntity.ok().body(note);
    }
}
