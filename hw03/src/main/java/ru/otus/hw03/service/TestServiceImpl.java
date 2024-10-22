package ru.otus.hw03.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw03.dao.QuestionDao;
import ru.otus.hw03.domain.Answer;
import ru.otus.hw03.domain.Question;
import ru.otus.hw03.domain.Student;
import ru.otus.hw03.domain.TestResult;


@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final LocalizedIOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printLine("Please answer the questions below");

        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var q: questions) {
            printQuestion(q);

            var isAnswerValid = false;
            int answerCount = q.answers().size();
            int chosenAnswerNum = getChosenAnswerNum(answerCount);

            isAnswerValid = isAnswerValid(q, chosenAnswerNum);
            testResult.applyAnswer(q, isAnswerValid);
        }
        return testResult;
    }

    private static boolean isAnswerValid(Question q, int chosenAnswerNum) {
        return q.answers().get(chosenAnswerNum - 1).isCorrect();
    }

    private int getChosenAnswerNum(int answerCount) {
        String prompt = "Type number of the right answer form 1 to " + answerCount;
        String errorMessage = "Incorrect symbol(s), try again";

        return ioService.readIntForRangeWithPrompt(1, answerCount, prompt, errorMessage);

    }

    private void printQuestion(Question q) {
        ioService.printLine(q.text());

        int counter = 1;
        for (Answer a: q.answers()) {
            ioService.printFormattedLine("%d. " + a.text(), counter);
            counter++;
        }

    }
}
