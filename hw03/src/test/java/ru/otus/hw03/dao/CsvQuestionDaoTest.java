package ru.otus.hw03.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import ru.otus.hw03.config.TestFileNameProvider;
import ru.otus.hw03.domain.Answer;
import ru.otus.hw03.domain.Question;
import ru.otus.hw03.exceptions.QuestionReadException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = CsvQuestionDao.class)
class CsvQuestionDaoTest {

    @MockBean
    private TestFileNameProvider fileNameProvider;
    @Autowired
    private CsvQuestionDao questionDao;

    @Test
    void csvQuestionDaoReturnsCorrectLines() {
        Mockito.when(fileNameProvider.getTestFileName()).thenReturn("questions.csv");
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
            throw new QuestionReadException("Can't read file " + expectedStringsFileName);
        }
        Assertions.assertTrue(actualStrings.containsAll(expectedStrings));
    }
}
