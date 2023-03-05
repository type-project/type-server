package io.sookmyung.type.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Random;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@Disabled
@DataJpaTest
@ActiveProfiles("test")
public abstract class JpaRepositoryTest<R extends JpaRepository<E, K>, E, K> {

    private EntityManager entityManager;
    private R repository;

    public JpaRepositoryTest(R repository, EntityManager entityManager) {
        this.repository = repository;
        this.entityManager = entityManager;
    }

    public abstract E generator();

    public abstract K getId(E e);

    public void isSameElements(E e1, E e2) {
        assertThat(e1).usingRecursiveComparison().isEqualTo(e2);
    }

    public String generateString() {
        // Generate 7 length Random String

        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        return new String(array, Charset.forName("UTF-8"));
    }

    @Order(1)
    @Test
    @DisplayName("JPA 테스트 : 레포지토리 비어있음")
    public void should_find_no_element_if_repository_is_empty() {
        List<E> es = repository.findAll();
        assertTrue(es.isEmpty());
    }

    @Order(2)
    @Test
    @DisplayName("JPA 테스트 : 한 개 저장")
    public void should_store_a_element() {
        E e = generator();
        E se = repository.save(e);

        isSameElements(e, se);
    }

    @Order(3)
    @Test
    @DisplayName("JPA 테스트 : 여러 개 저장")
    public void should_find_all_elements() {
        E e1 = generator();
        entityManager.persist(e1);

        E e2 = generator();
        entityManager.persist(e2);

        E e3 = generator();
        entityManager.persist(e3);

        List<E> es = repository.findAll();

        assertTrue(es.size() == 3);
        assertAll(
                () -> assertTrue(es.contains(e1)),
                () -> assertTrue(es.contains(e2)),
                () -> assertTrue(es.contains(e3))
        );
    }

    @Order(4)
    @Test
    @DisplayName("JPA 테스트 : 아이디로 조회")
    public void should_find_element_by_id() {
        E e1 = generator();
        entityManager.persist(e1);

        E e2 = generator();
        entityManager.persist(e2);

        E e = repository.findById(getId(e2)).orElse(null);

        isSameElements(e2, e);
    }

    @Order(5)
    @Test
    @DisplayName("JPA 테스트 : 아이디로 요소 삭제")
    public void should_delete_element_by_id() {
        E e1 = generator();
        entityManager.persist(e1);

        E e2 = generator();
        entityManager.persist(e2);

        E e3 = generator();
        entityManager.persist(e3);

        repository.deleteById(getId(e2));

        List<E> es = repository.findAll();

        assertEquals(es.size(), 2);
        assertAll(
                () -> es.contains(e1),
                () -> es.contains(e3)
        );
    }

    @Order(6)
    @Test
    @DisplayName("JPA 테스트 : 모든 요소 삭제")
    public void should_delete_all_elements() {
        E e1 = generator();
        entityManager.persist(e1);

        E e2 = generator();
        entityManager.persist(e2);

        repository.deleteAll();

        assertTrue(repository.findAll().isEmpty());
    }
}
