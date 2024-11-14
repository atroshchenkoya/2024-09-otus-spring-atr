package ru.otus.hw.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.JpaBookRepository;
import ru.otus.hw.repositories.JpaCommentRepository;
import ru.otus.hw.services.CommentServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({CommentServiceImpl.class, JpaCommentRepository.class, JpaBookRepository.class}) // Импортируем необходимые компоненты
public class CommentServiceImplTest {

    @Autowired
    private CommentServiceImpl commentService;

    @Autowired
    private JpaBookRepository bookRepository;

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED) // Отключаем транзакции для теста
    public void testInsertAndFetchComment() {
        // Сохраняем книгу, к которой будем привязывать комментарий
        Book book = new Book();
        book.setTitle("Test Book");
        book = bookRepository.save(book);

        // Добавляем комментарий к книге через сервис
        Comment savedComment = commentService.insert("Interesting book", book.getId());

        // Проверяем, что комментарий был сохранен и доступен для загрузки
        assertThat(savedComment).isNotNull();
        assertThat(savedComment.getId()).isGreaterThan(0);
        assertThat(savedComment.getContent()).isEqualTo("Interesting book");

        // Загружаем комментарий снова для проверки, что ленивые связи доступны
        Optional<Comment> fetchedComment = commentService.findById(savedComment.getId());
        assertThat(fetchedComment).isPresent();
        assertThat(fetchedComment.get().getBook()).isNotNull();
        assertThat(fetchedComment.get().getBook().getTitle()).isEqualTo("Test Book");
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void testDeleteComment() {
        // Сохраняем книгу и комментарий
        Book book = new Book();
        book.setTitle("Another Book");
        book = bookRepository.save(book);

        Comment comment = commentService.insert("Comment to delete", book.getId());
        long commentId = comment.getId();

        // Удаляем комментарий
        commentService.deleteById(commentId);

        // Проверяем, что комментарий был удален
        Optional<Comment> deletedComment = commentService.findById(commentId);
        assertThat(deletedComment).isNotPresent();
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void testLazyLoadingOnFindAll() {
        // Создаем и сохраняем книгу
        Book book = new Book();
        book.setTitle("Sample Book for Comments");
        book = bookRepository.save(book);

        // Добавляем комментарии
        commentService.insert("First Comment", book.getId());
        commentService.insert("Second Comment", book.getId());

        // Получаем все комментарии
        List<Comment> comments = commentService.findAll();

        // Проверяем, что книга в каждом комментарии загружается без LazyInitializationException
        for (Comment comment : comments) {
            assertThat(comment.getBook()).isNotNull();
            assertThat(comment.getBook().getTitle()).isEqualTo("Sample Book for Comments");
        }
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void testUpdateCommentContent() {
        // Сохраняем книгу и комментарий
        Book book = new Book();
        book.setTitle("Update Test Book");
        book = bookRepository.save(book);

        Comment comment = commentService.insert("Initial Comment", book.getId());

        // Обновляем комментарий
        Comment updatedComment = commentService.update(comment.getId(), "Updated Comment", book.getId());

        // Проверяем, что комментарий был обновлен
        assertThat(updatedComment).isNotNull();
        assertThat(updatedComment.getContent()).isEqualTo("Updated Comment");

        // Проверка, что связь с книгой все еще доступна
        assertThat(updatedComment.getBook()).isNotNull();
        assertThat(updatedComment.getBook().getTitle()).isEqualTo("Update Test Book");
    }
}
