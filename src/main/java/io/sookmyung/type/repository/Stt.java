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

    @ManyToOne
    @JoinColumn(name = "note_seq")
    private Note note;
}
