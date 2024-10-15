package ru.otus.hw.config;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppPropertiesTest implements TestConfig, TestFileNameProvider {

    private int rightAnswersCountToPass;

    private String testFileName;

}