package io.sookmyung.type.controller.dto;

import io.sookmyung.type.repository.Note;
import lombok.Getter;

@Getter
public class NoteListResponseDto {
    private final Long noteSeq;
    private final String name;
    private final String thumbnail;

    public NoteListResponseDto(Note entity) {
        this.noteSeq = entity.getNoteSeq();
        this.name = entity.getName();
        this.thumbnail = entity.getThumbnail();
    }
}
