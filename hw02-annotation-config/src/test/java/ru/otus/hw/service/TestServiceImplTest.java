package ru.otus.hw.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TestServiceImplTest {
    @Mock
    private CsvQuestionDao questionDao;
    @Mock
    StreamsIOService ioService;

    @Test
    public void rightAnswersAmountIsCorrect() {

        TestServiceImpl testService = new TestServiceImpl(ioService, questionDao);

        Answer a1q1 = new Answer("Fa Fq text", true);
        Answer a2q1 = new Answer("Sa Fq text", false);
        Question q1 = new Question("First q text", List.of(a1q1, a2q1));

        Mockito.when(questionDao.findAll()).thenReturn(List.of(q1, q1 ,q1 ,q1));
        Mockito.doNothing().when(ioService).printLine(Mockito.any());
        Mockito.when(ioService.readIntForRangeWithPrompt(
                Mockito.anyInt(),
                Mockito.anyInt(),
                Mockito.anyString(),
                Mockito.anyString()))
                .thenReturn(1);
        Mockito.doNothing().when(ioService).printFormattedLine(Mockito.any());

        TestResult testResult = testService.executeTestFor(new Student("pop", "hop"));

        Assertions.assertEquals(4, testResult.getRightAnswersCount());

    }

}
