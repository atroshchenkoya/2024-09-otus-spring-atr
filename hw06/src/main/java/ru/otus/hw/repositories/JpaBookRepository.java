package ru.otus.hw.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaBookRepository implements BookRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Book> findById(long id) {
        Book book = entityManager.find(Book.class, id);
        return Optional.ofNullable(book);
    }

    @Override
    public List<Book> findAll() {
        TypedQuery<Book> query = entityManager.createQuery("SELECT b FROM Book b", Book.class);
        query.setHint("javax.persistence.fetchgraph", entityManager.getEntityGraph("Book.full"));
        return query.getResultList();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            entityManager.persist(book);
            return book;
        } else {
            return update(book);
        }
    }

    @Override
    public void deleteById(long id) {
        Book book = entityManager.find(Book.class, id);
        if (book == null) {
            throw new EntityNotFoundException("No book found with id " + id + " to delete.");
        }
        entityManager.remove(book);
    }

    public Book update(Book book) {
        Book existingBook = entityManager.find(Book.class, book.getId());
        if (existingBook == null) {
            throw new EntityNotFoundException("No book found with id " + book.getId() + " to update.");
        }
        return entityManager.merge(book);
    }
}
