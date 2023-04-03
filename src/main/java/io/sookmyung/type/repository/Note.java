package io.sookmyung.type.repository;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@Entity
@Getter
@ToString
@NoArgsConstructor
@Table(name = "note")
public class Note extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_seq", nullable = false)
    private Long noteSeq;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "slide_url", columnDefinition = "TEXT")
    private String slideUrl;

    @Setter
    @Column(name = "note_summary", columnDefinition = "TEXT")
    private String summary;

    @Column(name = "user_seq", nullable = false)
    private Long userSeq;

    @Column(name = "thumbnail")
    private String thumbnail;
}
