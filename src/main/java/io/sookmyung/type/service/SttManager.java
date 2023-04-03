package io.sookmyung.type.service;

import io.sookmyung.type.controller.dto.SttListDto;

import java.util.List;

public interface SttManager {
    String saveStt(Long noteSeq, Integer slideIdx, String content);
    List<SttListDto> findAllContent(Long noteSeq);
    String findContentsBySlide(Long noteSeq, Integer slideIdx);
}
