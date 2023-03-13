package io.sookmyung.type.repository;

import jakarta.persistence.EntityManager;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
class SttRepositoryTest extends JpaRepositoryTest<SttRepository, Stt, Long> {
    private final Logger logger = LoggerFactory.getLogger(SttRepositoryTest.class);

    private SttRepository repository;
    private EntityManager entityManager;
    private NoteRepository noteRepository;

    @Autowired
    public SttRepositoryTest(SttRepository repository, EntityManager entityManager, NoteRepository noteRepository) {
        super(repository, entityManager);
        this.repository = repository;
        this.entityManager = entityManager;
        this.noteRepository = noteRepository;
    }

    @Override
    public Stt generator() {
        Random random = new Random();
        Note note = Note.builder()
                .userSeq(random.nextLong())
                .slideUrl("https://" + RandomString.make(10))
                .build();
        noteRepository.save(note);
        entityManager.persist(note);

        return Stt.builder()
                .slideIdx(random.nextInt())
                .content(RandomString.make(100))
                .noteSeq(note.getNoteSeq())
                .note(note)
                .build();
    }

    @Override
    public Long getId(Stt stt) {
        return stt.getSttSeq();
    }

    @Test
    @DisplayName("STT 테스트 : FK(noteSeq)로 STT 조회")
    void find_all_by_noteSeq() {
        Stt stt = generator();
        Stt save = repository.save(stt);
        Long noteSeq = save.getNote().getNoteSeq();

        List<Stt> sttList = repository.findAllByNote_NoteSeq(noteSeq);

        assertEquals(1, sttList.size());
    }

    @Test
    @DisplayName("STT 테스트 : FK(noteSeq)와 slideIdx로 조회")
    void find_all_by_noteSeq_and_slideIdx() {
        Stt stt = generator();
        Stt save = repository.save(stt);
        Long noteSeq = save.getNote().getNoteSeq();
        Integer slideIdx = save.getSlideIdx();

        List<Stt> sttList = repository.findAllBySlideIdxAndNote_NoteSeq(slideIdx, noteSeq);

        assertEquals(1, sttList.size());
    }
}
