package ru.otus.hw.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.JpaBookRepository;
import ru.otus.hw.repositories.JpaCommentRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({JpaCommentRepository.class, JpaBookRepository.class})
public class JpaCommentRepositoryTest {

    @Autowired
    private JpaCommentRepository commentRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testSaveAndFindComment() {
        Book book = new Book();
        book.setTitle("Test Book");
        book = bookRepository.save(book);
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
        book = bookRepository.save(book);
        Comment comment = new Comment();
        comment.setContent("To be deleted");
        comment.setBook(book);
        Comment savedComment = commentRepository.save(comment);

        commentRepository.deleteById(savedComment.getId());

        Optional<Comment> foundComment = commentRepository.findById(savedComment.getId());
        assertThat(foundComment).isNotPresent();
    }
}
