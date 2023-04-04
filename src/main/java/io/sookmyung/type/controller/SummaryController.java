package io.sookmyung.type.controller;

import io.sookmyung.type.controller.dto.SlideSummaryResponseDto;
import io.sookmyung.type.service.NoteManagerImpl;
import io.sookmyung.type.service.SlideManagerImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/summary")
public class SummaryController {
    @Autowired
    private SlideManagerImpl slideService;

    @GetMapping(value = "/slide")
    public ResponseEntity<SlideSummaryResponseDto> summarySlide(@RequestParam("noteSeq") Long noteSeq,
                                                                @RequestParam("slideIdx") Integer slideIdx) {
        log.info("/summary/slide?" + noteSeq + "&" + slideIdx);

        String summary = slideService.summarySlide(noteSeq, slideIdx);

        if (summary == null) {
            SlideSummaryResponseDto failedDto = SlideSummaryResponseDto.builder().slideIdx(slideIdx).summary("요약에 실패하였습니다.").build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(failedDto);
        } else {
            SlideSummaryResponseDto successDto = SlideSummaryResponseDto.builder().slideIdx(slideIdx).summary(summary).build();
            return ResponseEntity.ok(successDto);
        }
    }
}
