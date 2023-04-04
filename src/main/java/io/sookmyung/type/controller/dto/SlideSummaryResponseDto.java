package io.sookmyung.type.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SlideSummaryResponseDto {
    private final Integer slideIdx;
    private final String summary;

    @Builder
    public SlideSummaryResponseDto(Integer slideIdx, String summary) {
        this.slideIdx = slideIdx;
        this.summary = summary;
    }
}
