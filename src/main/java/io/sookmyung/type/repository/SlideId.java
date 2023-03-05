package io.sookmyung.type.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@Data
@Embeddable
@NoArgsConstructor
public class SlideId implements Serializable {

    @Column(name = "note_seq", nullable = false)
    private Long noteSeq;

    @Column(name = "slide_idx", nullable = false)
    private Integer slideIdx;
}
