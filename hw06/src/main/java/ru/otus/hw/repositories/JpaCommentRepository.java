package ru.otus.hw.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaCommentRepository implements CommentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Comment> findById(long id) {
        Comment comment = entityManager.find(Comment.class, id);
        return Optional.ofNullable(comment);
    }

    @Override
    public List<Comment> findAll() {
        String jpql = "SELECT c FROM Comment c";
        TypedQuery<Comment> query = entityManager.createQuery(jpql, Comment.class);
        return query.getResultList();
    }

    @Override
    public List<Comment> findByBookId(long bookId) {
        String jpql = "SELECT c FROM Comment c WHERE c.book.id = :bookId";
        TypedQuery<Comment> query = entityManager.createQuery(jpql, Comment.class);
        query.setParameter("bookId", bookId);
        return query.getResultList();
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == 0) {
            entityManager.persist(comment);
            return comment;
        } else {
            return update(comment);
        }
    }

    @Override
    public void deleteById(long id) {
        Comment comment = entityManager.find(Comment.class, id);
        if (comment == null) {
            throw new EntityNotFoundException("No comment found with id " + id + " to delete.");
        }
        entityManager.remove(comment);
    }

    public Comment update(Comment comment) {
        Comment existingComment = entityManager.find(Comment.class, comment.getId());
        if (existingComment == null) {
            throw new EntityNotFoundException("No comment found with id " + comment.getId() + " to update.");
        }
        existingComment.setContent(comment.getContent());
        return entityManager.merge(existingComment);
    }
}
