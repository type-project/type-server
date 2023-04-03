package io.sookmyung.type.controller;

import io.sookmyung.type.controller.dto.SttResponseDto;
import io.sookmyung.type.service.SpeechToTextService;
import io.sookmyung.type.service.SttManagerImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("stt")
public class SpeechToTextController {
    @Autowired
    private SpeechToTextService speechToTextService;
    @Autowired
    private SttManagerImpl sttService;

    // for connection test
    @GetMapping("/")
    public ResponseEntity<String> index() {
        log.info("/stt/");
        return ResponseEntity.ok("Greetings from Type application!");
    }

    /**
     * 녹음 파일을 받아서 텍스트로 변환하여 저장하고 반환
     *
     * @param audioFile 오디오 파일
     * @param noteSeq   노트 번호
     * @param slideIdx  슬라이드 번호
     * @return 녹음 파일을 변환한 텍스트
     */
    @PostMapping(value = "/audio", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SttResponseDto> handleAudioMessage(@RequestParam("audioFile") MultipartFile audioFile,
                                                             @RequestParam("noteSeq") Long noteSeq,
                                                             @RequestParam("slideIdx") Integer slideIdx) {
        log.info("/stt/audio " + " noteSeq = " + noteSeq + " slideIdx = " + slideIdx);

        String transcribe = speechToTextService.transcribe(audioFile);
        String saveStt = sttService.saveStt(noteSeq, slideIdx, transcribe);

        System.out.println("saveStt = " + saveStt);
        if (saveStt == null) {
            SttResponseDto failedDto = SttResponseDto.builder()
                    .slideIdx(0)
                    .content("변환 결과가 없습니다")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(failedDto);
        } else {
            SttResponseDto successDto = SttResponseDto.builder()
                    .slideIdx(slideIdx)
                    .content(saveStt)
                    .build();
            return ResponseEntity.ok().body(successDto);
        }
    }
}
