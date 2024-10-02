package ru.otus.spring.service;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

public interface TestRunnerService {
    void run() throws FileNotFoundException, URISyntaxException;
}
