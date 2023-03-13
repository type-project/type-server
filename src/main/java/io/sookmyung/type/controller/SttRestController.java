package io.sookmyung.type.controller;

import io.sookmyung.type.service.SpeechToTextService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("app")
public class SttRestController {

    @Autowired
    private SpeechToTextService sttService;

    // for connection test
    @GetMapping("/")
    public ResponseEntity<String> index() {
        return ResponseEntity.ok("Greetings from Type application!");
    }

    /**
     * 녹음 파일을 받아서 텍스트로 변환하여 반환
     *
     * @param audioFile 오디오 파일
     * @param userSeq   유저 일련번호
     * @param slideIdx  슬라이드 번호
     * @return 녹음 파일을 변환한 텍스트
     */
    @PostMapping(value = "/audio", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> handleAudioMessage(@RequestParam("audioFile") MultipartFile audioFile,
                                                     @RequestParam("userSeq") Long userSeq,
                                                     @RequestParam("slideIdx") Integer slideIdx) throws IOException {

        String transcribe = sttService.transcribe(audioFile);

        return ResponseEntity.ok().body(transcribe);
    }
}
