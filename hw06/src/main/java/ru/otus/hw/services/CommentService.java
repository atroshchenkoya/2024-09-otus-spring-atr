package ru.otus.hw.services;

import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Optional<Comment> findById(long id);

    List<Comment> findAll();

    List<Comment> findByBookId(long bookId);

    Comment insert(String content, long bookId);

    Comment update(long id, String content, long bookId);

    void deleteById(long id);
}