package ru.otus.hw03;

import org.springframework.shell.command.annotation.CommandScan;
import ru.otus.hw03.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
@CommandScan
public class HomeWorkApplication {
	public static void main(String[] args) {
		SpringApplication.run(HomeWorkApplication.class, args);
	}
}
