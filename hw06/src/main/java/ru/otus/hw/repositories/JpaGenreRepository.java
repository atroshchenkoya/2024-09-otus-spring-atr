package ru.otus.hw.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class JpaGenreRepository implements GenreRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Genre> findAll() {
        String jpql = "SELECT g FROM Genre g";
        return entityManager.createQuery(jpql, Genre.class).getResultList();
    }

    @Override
    public Optional<Genre> findById(long id) {
        Genre genre = entityManager.find(Genre.class, id);
        return Optional.ofNullable(genre);
    }

    @Override
    public Genre save(Genre genre) {
        if (genre.getId() == 0) {
            entityManager.persist(genre);
            return genre;
        } else {
            return entityManager.merge(genre);
        }
    }

    @Override
    public void deleteById(long id) {
        Genre genre = entityManager.find(Genre.class, id);
        if (genre != null) {
            entityManager.remove(genre);
        }
    }
}
