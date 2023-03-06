package io.sookmyung.type.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SlideRepository extends JpaRepository<Slide, SlideId> {
    List<Slide> findAllByNote_NoteSeq(Long noteSeq);
}