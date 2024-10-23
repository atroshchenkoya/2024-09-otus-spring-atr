package ru.otus.hw03;

import ru.otus.hw03.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.hw03.service.TestRunnerService;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class Hw03Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Hw03Application.class, args);
		var testRunnerService = context.getBean(TestRunnerService.class);
		testRunnerService.run();
	}
}
