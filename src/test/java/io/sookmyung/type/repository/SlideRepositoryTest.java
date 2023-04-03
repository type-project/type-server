package io.sookmyung.type.repository;

import jakarta.persistence.EntityManager;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
class SlideRepositoryTest extends JpaRepositoryTest<SlideRepository, Slide, SlideId> {

    private SlideRepository repository;
    private NoteRepository noteRepository;
    private EntityManager entityManager;

    @Autowired
    public SlideRepositoryTest(SlideRepository repository, EntityManager entityManager, NoteRepository noteRepository) {
        super(repository, entityManager);
        this.repository = repository;
        this.entityManager = entityManager;
        this.noteRepository = noteRepository;
    }


    @Override
    public Slide generator() {
        Random random = new Random();
        Note note = Note.builder()
                .userSeq(random.nextLong())
                .name(RandomString.make(10))
                .slideUrl("https://" + RandomString.make(10))
                .build();
        noteRepository.save(note);
        entityManager.persist(note);

        SlideId slideId = SlideId.builder()
                .slideIdx(random.nextInt())
                .noteSeq(note.getNoteSeq())
                .build();

        return Slide.builder()
                .slideId(slideId)
                .note(note)
                .summary(RandomString.make(20))
                .build();
    }

    @Override
    public SlideId getId(Slide slide) {
        return slide.getSlideId();
    }

    @Test
    @DisplayName("Slide 테스트 : FK(noteSeq)로 조회")
    void find_all_by_noteSeq() {
        Slide slide = generator();
        Slide save = repository.save(slide);
        Long noteSeq = save.getNote().getNoteSeq();

        List<Slide> slideList = repository.findAllByNote_NoteSeq(noteSeq);

        assertEquals(1, slideList.size());
    }
}