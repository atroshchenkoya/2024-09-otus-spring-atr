package ru.otus.hw.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.otus.hw.config.AppPropertiesTest;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

class CsvQuestionDaoTest {

    static TestFileNameProvider testFileNameProvider;
    static QuestionDao questionDao;

    @BeforeAll
    static void beforeAll() {
        testFileNameProvider = new AppPropertiesTest(3,"questionsTest.csv");
        questionDao = new CsvQuestionDao(testFileNameProvider);
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
