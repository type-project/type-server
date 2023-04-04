package io.sookmyung.type.controller.dto;

import io.sookmyung.type.repository.Note;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class NoteResponseDto {
    private final Long noteSeq;
    private final String name;
    private final String slideUrl;

    private List<SttListDto> sttList;

    public void sttList(List<SttListDto> sttList) {
        this.sttList = sttList;
    }

    public NoteResponseDto(Note note) {
        this.noteSeq = note.getNoteSeq();
        this.name = note.getName();
        this.slideUrl = note.getSlideUrl();
    }
}
