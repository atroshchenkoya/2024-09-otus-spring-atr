package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.services.CommentService;
import ru.otus.hw.converters.CommentConverter;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class CommentCommands {

    private final CommentService commentService;
    private final CommentConverter commentConverter;

    @ShellMethod(value = "Find all comments", key = "ac")
    public String findAllComments() {
        return commentService.findAll().stream()
                .map(commentConverter::commentToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    @ShellMethod(value = "Find comment by id", key = "ccid")
    public String findCommentById(long id) {
        return commentService.findById(id)
                .map(commentConverter::commentToString)
                .orElse("Comment with id %d not found".formatted(id));
    }

    @ShellMethod(value = "Insert comment", key = "cins")
    public String insertComment(String content, long bookId) {
        var savedComment = commentService.insert(content, bookId);
        return commentConverter.commentToString(savedComment);
    }

    @ShellMethod(value = "Update comment", key = "cupd")
    public String updateComment(long id, String content, long bookId) {
        var updatedComment = commentService.update(id, content, bookId);
        return commentConverter.commentToString(updatedComment);
    }

    @ShellMethod(value = "Delete comment by id", key = "cdel")
    public void deleteComment(long id) {
        commentService.deleteById(id);
    }

    @ShellMethod(value = "Find comments by book id", key = "cbybook")
    public String findCommentsByBookId(long bookId) {
        return commentService.findByBookId(bookId).stream()
                .map(commentConverter::commentToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }
}
