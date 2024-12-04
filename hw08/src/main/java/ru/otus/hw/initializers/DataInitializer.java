package ru.otus.hw.initializers;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    public DataInitializer(AuthorRepository authorRepository, GenreRepository genreRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) {
        Author author = new Author(null, "Leo Tolstoy");
        author = authorRepository.save(author);

        Genre genre = new Genre(null, "Historical Fiction");
        genre = genreRepository.save(genre);

        Book book = new Book(null, "War and Peace", author, genre);
        bookRepository.save(book);
    }
}
