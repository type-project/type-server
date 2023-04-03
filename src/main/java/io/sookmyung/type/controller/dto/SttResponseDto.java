package io.sookmyung.type.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SttResponseDto {
    private final Integer slideIdx;
    private final String content;

    @Builder
    public SttResponseDto(Integer slideIdx, String content) {
        this.slideIdx = slideIdx;
        this.content = content;
    }
}
