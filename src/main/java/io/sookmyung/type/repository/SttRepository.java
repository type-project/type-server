package io.sookmyung.type.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SttRepository extends JpaRepository<Stt, Long> {
    List<Stt> findAllByNote(Note note);

    List<Stt> findSttsByNoteAndSlideIdx(Note note, Integer slideIdx);
}