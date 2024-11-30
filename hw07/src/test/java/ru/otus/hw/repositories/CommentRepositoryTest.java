package ru.otus.hw.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CommentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void testSaveAndFindComment() {
        Book book = new Book();
        book.setTitle("Test Book");
        entityManager.persistAndFlush(book);

        Comment comment = new Comment();
        comment.setContent("Great book!");
        comment.setBook(book);

        Comment savedComment = commentRepository.save(comment);

        assertThat(savedComment).isNotNull();
        assertThat(savedComment.getId()).isGreaterThan(0);
        assertThat(savedComment.getContent()).isEqualTo("Great book!");

        Optional<Comment> foundComment = commentRepository.findById(savedComment.getId());
        assertThat(foundComment).isPresent();
        assertThat(foundComment.get().getContent()).isEqualTo("Great book!");
    }

    @Test
    public void testDeleteComment() {
        Book book = new Book();
        book.setTitle("Test Book");
        entityManager.persistAndFlush(book);

        Comment comment = new Comment();
        comment.setContent("To be deleted");
        comment.setBook(book);

        Comment savedComment = commentRepository.save(comment);
        commentRepository.deleteById(savedComment.getId());

        Optional<Comment> foundComment = commentRepository.findById(savedComment.getId());
        assertThat(foundComment).isNotPresent();
    }
}

