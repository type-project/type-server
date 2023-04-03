package io.sookmyung.type.service;

public interface SlideManager {
    // Todo 슬라이드 요약 조회/요청/저장
    String summarySlide(Long noteSeq, Integer slideIdx);

    String findSlideSummaries(Long noteSeq);
}
