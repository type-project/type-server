package io.sookmyung.type.repository;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@Entity
@Getter
@ToString
@NoArgsConstructor
@Table(name = "speech_to_text")
public class Stt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stt_seq", nullable = false)
    private Long sttSeq;

    @Column(name = "slide_idx", nullable = false)
    private Integer slideIdx;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "note_seq", nullable = false)
    private Long noteSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "note_seq", referencedColumnName = "note_seq", insertable = false, updatable = false)
    private Note note;
}
