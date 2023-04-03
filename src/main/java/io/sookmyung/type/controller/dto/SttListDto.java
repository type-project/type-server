package io.sookmyung.type.controller.dto;

import io.sookmyung.type.repository.Stt;
import lombok.Getter;

@Getter
public class SttListDto {
    private final Integer slideIdx;
    private final String content;

    public SttListDto(Stt entity) {
        this.slideIdx = entity.getSlideIdx();
        this.content = entity.getContent();
    }
}
