package io.sookmyung.type.repository;

import jakarta.persistence.*;
import lombok.*;

@ToString
@Builder
@AllArgsConstructor
@Entity
@Getter
@NoArgsConstructor
@Table(name = "quiz")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_seq", nullable = false)
    private Long quizSeq;

    @Column(name = "keyword", nullable = false)
    private String keyword;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "note_seq", nullable = false)
    private Long noteSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "note_seq", referencedColumnName = "note_seq", insertable = false, updatable = false)
    private Note note;
}
