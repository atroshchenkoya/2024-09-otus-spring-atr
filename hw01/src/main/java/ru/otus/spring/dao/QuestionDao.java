package ru.otus.spring.dao;

import ru.otus.spring.domain.Question;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.List;

public interface QuestionDao {
    List<Question> findAll() throws FileNotFoundException, URISyntaxException;
}
