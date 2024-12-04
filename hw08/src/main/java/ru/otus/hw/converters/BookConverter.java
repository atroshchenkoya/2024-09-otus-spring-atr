package ru.otus.hw.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.CommentRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class BookConverter {
    private final AuthorConverter authorConverter;
    private final GenreConverter genreConverter;
    private final CommentConverter commentConverter;
    private final CommentRepository commentRepository;

    public String bookToString(Book book) {
        List<Comment> comments = commentRepository.findByBookId(book.getId());
        String commentsString = comments.stream()
                .map(commentConverter::commentToString)
                .collect(Collectors.joining(", "));

        return "Id: %s, title: %s, author: {%s}, genre: [%s], comments: [%s]".formatted(
                book.getId(),
                book.getTitle(),
                authorConverter.authorToString(book.getAuthor()),
                genreConverter.genreToString(book.getGenre()),
                commentsString.isEmpty() ? "No comments" : commentsString
        );
    }
}