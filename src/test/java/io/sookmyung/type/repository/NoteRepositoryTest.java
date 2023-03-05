package io.sookmyung.type.repository;

import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)   // 메소드의 테스트 순서 지정
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class NoteRepositoryTest {

    private final Long USER_SEQ = 1L;

    @Autowired
    private NoteRepository repository;

    @Autowired
    private TestEntityManager em;

    protected Note generateNote(Long userSeq) {
        return Note.builder().userSeq(userSeq)
                .slideUrl("https://" + RandomString.make(10))
                .build();
    }

    @Order(1)
    @Test
    @DisplayName("노트 레포지토리 : 새 노트 저장")
    void save_note() {
        //given
        Note note = generateNote(USER_SEQ);

        //when
        Note saveNote = repository.save(note);

        //then
        assertEquals(note.getUserSeq(), saveNote.getUserSeq());
    }

    @Order(2)
    @Test
    @DisplayName("노트 레포지토리 : 노트 조회")
    void find_note() {
        //given
        Note note = generateNote(USER_SEQ);
        em.persist(note);

        //when
        Note findNote = em.find(Note.class, note.getNoteSeq());

        //then
        assertEquals(note.getUserSeq(), findNote.getUserSeq());
    }

    @Order(3)
    @Test
    @DisplayName("노트 레포지토리 : 노트 목록 조회")
    void find_note_list() {
        //given
        repository.save(generateNote(USER_SEQ));
        repository.save(generateNote(USER_SEQ));
        repository.save(generateNote(USER_SEQ));

        //when
        List<Note> findNotes = repository.findNotesByUserSeq(USER_SEQ);

        //then
        assertEquals(findNotes.size(), 3);
    }

    @Order(4)
    @Test
    @DisplayName("노트 레포지토리 : 노트 삭제")
    void delete_note() {
        //given
        Note saveNote = repository.save(generateNote(USER_SEQ));

        //when
        repository.deleteById(saveNote.getNoteSeq());

        //then
        Note deleteNote = repository.findById(saveNote.getNoteSeq()).orElse(null);
        assertNull(deleteNote);
    }
}