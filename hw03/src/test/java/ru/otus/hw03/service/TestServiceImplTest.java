package ru.otus.hw03.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw03.dao.CsvQuestionDao;
import ru.otus.hw03.domain.Answer;
import ru.otus.hw03.domain.Question;
import ru.otus.hw03.domain.Student;
import ru.otus.hw03.domain.TestResult;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class TestServiceImplTest {

    @InjectMocks
    private TestServiceImpl testService;
    @Mock
    private CsvQuestionDao questionDao;
    @Mock
    private LocalizedIOService ioService;

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
