package ru.otus.hw04.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw04.dao.CsvQuestionDao;
import ru.otus.hw04.domain.Answer;
import ru.otus.hw04.domain.Question;
import ru.otus.hw04.domain.Student;
import ru.otus.hw04.domain.TestResult;

import java.util.List;

@SpringBootTest(classes = TestServiceImpl.class, properties = "spring.shell.interactive.enabled=false")
class TestServiceImplTest {

    @MockBean
    private CsvQuestionDao questionDao;
    @MockBean
    private LocalizedIOService ioService;
    @Autowired
    private TestServiceImpl testService;

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
