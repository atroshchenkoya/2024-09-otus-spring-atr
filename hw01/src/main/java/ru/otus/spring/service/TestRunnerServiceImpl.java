package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

@RequiredArgsConstructor
public class TestRunnerServiceImpl implements TestRunnerService {

    private final TestService testService;

    @Override
    public void run() throws FileNotFoundException, URISyntaxException {
        testService.executeTest();
    }
}
