package ru.otus.hw.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class TestServiceImplTest {

    private AutoCloseable closeable;

    @InjectMocks
    private TestServiceImpl testService;
    @Mock
    private CsvQuestionDao questionDao;
    @Mock
    private StreamsIOService ioService;

    @BeforeEach
    public void prepare() {
        testService = null;
        closeable = MockitoAnnotations.openMocks(this);
    }
    @AfterEach
    void closeService() {
        try {
            closeable.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void countOfRightAnswersShouldBeCorrect() {

        Answer answer1Question1 = new Answer("Fa Fq text", true);
        Answer answer2Question1 = new Answer("Sa Fq text", false);
        Question question1 = new Question("First q text", List.of(answer1Question1, answer2Question1));

        Mockito.when(questionDao.findAll()).thenReturn(List.of(question1, question1 ,question1 ,question1));
        Mockito.doNothing().when(ioService).printLine(Mockito.any());
        Mockito.when(ioService.readIntForRangeWithPrompt(
                Mockito.anyInt(),
                Mockito.anyInt(),
                Mockito.anyString(),
                Mockito.anyString()))
                .thenReturn(1);
        Mockito.doNothing().when(ioService).printFormattedLine(Mockito.any(), Mockito.any());

        TestResult testResult = testService.executeTestFor(new Student("pop", "hop"));

        Assertions.assertEquals(4, testResult.getRightAnswersCount());

    }

}
