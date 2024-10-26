package ru.otus.hw03.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw03.config.TestFileNameProvider;
import ru.otus.hw03.domain.Answer;
import ru.otus.hw03.domain.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class CsvQuestionDaoTest {

    @InjectMocks
    private CsvQuestionDao questionDao;
    @Mock
    private TestFileNameProvider fileNameProvider;

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
            throw new QuestionReadException(e);
        }
        Assertions.assertTrue(actualStrings.containsAll(expectedStrings));
    }
}
