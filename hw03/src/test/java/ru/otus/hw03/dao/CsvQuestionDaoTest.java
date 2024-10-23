package ru.otus.hw03.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import ru.otus.hw03.Hw03Application;
import ru.otus.hw03.domain.Answer;
import ru.otus.hw03.domain.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@ContextConfiguration
@TestPropertySource("classpath:application.yaml")
class CsvQuestionDaoTest {

    static QuestionDao questionDao;

    @BeforeAll
    static void beforeAll() {
        ConfigurableApplicationContext context = SpringApplication.run(Hw03Application.class);
        questionDao = context.getBean(QuestionDao.class);
    }

    @Test
    void CsvQuestionDaoReturnsCorrectLines2() {
        String expectedStringsFileName = "expectedLines.txt";
        List<String> expectedStrings = new ArrayList<>();
        List<String> actualStrings = new ArrayList<>();

        List<Question> questions = questionDao.findAll();

        for (Question q: questions) {
            actualStrings.add(q.text());
            int counter = 1;
            for (Answer a: q.answers()) {
                actualStrings.add(counter + ". " + a.text());
                counter++;
            }
            actualStrings.add("------------------------------");
        }
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(expectedStringsFileName)) {
            assert inputStream != null;
            try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                 BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
                while (bufferedReader.ready()) {
                    expectedStrings.add(bufferedReader.readLine());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(actualStrings.containsAll(expectedStrings));
    }
}