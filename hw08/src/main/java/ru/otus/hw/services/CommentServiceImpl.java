package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    @Override
    public Optional<Comment> findById(long id) {
        return commentRepository.findById(String.valueOf(id));
    }

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public List<Comment> findByBookId(long bookId) {
        return commentRepository.findByBookId(String.valueOf(bookId));
    }

    @Override
    public Comment insert(String content, long bookId) {
        return save(null, content, bookId);
    }

    @Override
    public Comment update(long id, String content, long bookId) {
        return save(String.valueOf(id), content, bookId);
    }

    @Override
    public void deleteById(long id) {
        commentRepository.deleteById(String.valueOf(id));
    }

    private Comment save(String id, String content, long bookId) {
        Book book = bookRepository.findById(String.valueOf(bookId))
                .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(bookId)));
        Comment comment = new Comment(id, content, book);
        return commentRepository.save(comment);
    }
}