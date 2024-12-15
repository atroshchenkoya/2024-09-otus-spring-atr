package ru.otus.hw.initializers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.repositories.GenreRepository;

@Component
@Slf4j
@Order(1)
public class DataInitializer implements CommandLineRunner {

    // Initializer отработает после ввода команды exit

    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

    public DataInitializer(
            AuthorRepository authorRepository,
            GenreRepository genreRepository,
            BookRepository bookRepository,
            CommentRepository commentRepository) {
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public void run(String... args) {
        log.info("Initializing started!!!");
        // Создаём автора
        Author author = new Author(null, "Leo Tolstoy");
        author = authorRepository.save(author);

        // Создаём жанр
        Genre genre = new Genre(null, "Historical Fiction");
        genre = genreRepository.save(genre);

        // Создаём книгу
        Book book = new Book(null, "War and Peace", author, genre);
        book = bookRepository.save(book);

        // Создаём комментарии для книги
        Comment comment1 = new Comment(null, "A masterpiece of literature!", book);
        Comment comment2 = new Comment(null, "Quite a lengthy read, but worth it.", book);
        Comment comment3 = new Comment(null, "Loved the historical detail and characters.", book);

        // Сохраняем комментарии
        commentRepository.save(comment1);
        commentRepository.save(comment2);
        commentRepository.save(comment3);

        System.out.println("DataInitializer completed!!!");
    }
}

