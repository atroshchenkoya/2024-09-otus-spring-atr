package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.List;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public void executeTest() throws FileNotFoundException, URISyntaxException {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");

        List<Question> questions = questionDao.findAll();

        for (Question q: questions) {
            ioService.printLine(q.text());
            int counter = 1;
            for (Answer a: q.answers()) {
                ioService.printFormattedLine("%d. " + a.text(), counter);
                counter++;
            }
            ioService.printLine("------------------------------");
        }
    }
}
