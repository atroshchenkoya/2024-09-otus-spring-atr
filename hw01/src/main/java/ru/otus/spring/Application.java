package ru.otus.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.service.TestRunnerService;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

public class Application {
    public static void main(String[] args) throws FileNotFoundException, URISyntaxException {

        //Прописать бины в spring-context.xml и создать контекст на его основе
        ApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        var testRunnerService = context.getBean(TestRunnerService.class);
        testRunnerService.run();

    }
}