package io.sookmyung.type.repository;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Entity
@Getter
@NoArgsConstructor
@Table(name = "slide")
public class Slide {

    @EmbeddedId
    private SlideId slideId;

    @Column(name = "slide_summary", nullable = false, columnDefinition = "TEXT")
    private String summary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "note_seq", referencedColumnName = "note_seq", insertable = false, updatable = false)
    private Note note;
}
