package ru.otus.spring.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.dao.CsvQuestionDao;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;

import java.util.ArrayList;
import java.util.List;

class QuestionDaoTest {

    @Test
    void CsvQuestionDaoReturnsCorrectLines() {

        ApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        var questionDao = context.getBean(CsvQuestionDao.class);

        String correctLine = """
                Is there life on Mars?
                1. Science doesn't know this yet
                2. Certainly. The red UFO is from Mars. And green is from Venus
                3. Absolutely not
                ------------------------------
                How should resources be loaded form jar in Java?
                1. ClassLoader#geResourceAsStream or ClassPathResource#getInputStream
                2. ClassLoader#geResource#getFile + FileReader
                3. Wingardium Leviosa
                ------------------------------
                Which option is a good way to handle the exception?
                1. @SneakyThrow
                2. e.printStackTrace()
                3. Rethrow with wrapping in business exception (for example, QuestionReadException)
                4. Ignoring exception
                ------------------------------
                How to get a good mark?
                1. Try hard
                2. Learn everything
                3. Be lucky
                4. Ignore your tutor
                ------------------------------
                
                """;
        List<String> expectedStrings = new ArrayList<>(List.of(correctLine.split("\n")));
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
        Assertions.assertTrue(actualStrings.containsAll(expectedStrings));

    }

}