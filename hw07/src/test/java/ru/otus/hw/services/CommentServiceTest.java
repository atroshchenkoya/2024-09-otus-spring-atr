package ru.otus.hw.services;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.hibernate.Hibernate;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Import({CommentServiceImpl.class})
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testInsertAndFetchComment() {
        Book book = new Book();
        book.setTitle("Test Book");
        book = bookRepository.save(book);

        Comment savedComment = commentService.insert("Interesting book", book.getId());

        assertThat(savedComment).isNotNull();
        assertThat(savedComment.getId()).isGreaterThan(0);
        assertThat(savedComment.getContent()).isEqualTo("Interesting book");

        Hibernate.initialize(savedComment.getBook());

        Optional<Comment> fetchedComment = commentService.findById(savedComment.getId());
        assertThat(fetchedComment).isPresent();
        assertThat(fetchedComment.get().getBook()).isNotNull();
        assertThat(fetchedComment.get().getBook().getTitle()).isEqualTo("Test Book");
    }

    @Test
    public void testLazyLoadingOnFindAll() {
        Book book = new Book();
        book.setTitle("Sample Book for Comments");
        book = bookRepository.save(book);

        commentService.insert("First Comment", book.getId());
        commentService.insert("Second Comment", book.getId());
        List<Comment> comments = commentService.findAll();

        for (Comment comment : comments) {
            Hibernate.initialize(comment.getBook());
            assertThat(comment.getBook()).isNotNull();
            assertThat(comment.getBook().getTitle()).isEqualTo("Sample Book for Comments");
        }
    }

    @Test
    public void testUpdateCommentContent() {
        Book book = new Book();
        book.setTitle("Update Test Book");
        book = bookRepository.save(book);
        Comment comment = commentService.insert("Initial Comment", book.getId());

        Comment updatedComment = commentService.update(comment.getId(), "Updated Comment", book.getId());

        assertThat(updatedComment).isNotNull();
        assertThat(updatedComment.getContent()).isEqualTo("Updated Comment");
        Hibernate.initialize(updatedComment.getBook());
        assertThat(updatedComment.getBook()).isNotNull();
        assertThat(updatedComment.getBook().getTitle()).isEqualTo("Update Test Book");
    }

    @Test
    public void testEntityNotFoundExceptionOnInsert() {
        long nonExistBookId = 999L;

        assertThatThrownBy(() -> commentService.insert("Invalid Comment", nonExistBookId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Book with id " + nonExistBookId + " not found");
    }

    @Test
    public void testEntityNotFoundExceptionOnUpdate() {
        long nonExistCommentId = 999L;
        long nonExistBookId = 999L;

        assertThatThrownBy(() -> commentService.update(nonExistCommentId, "Updated Content", nonExistBookId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Book with id " + nonExistBookId + " not found");
    }

}
