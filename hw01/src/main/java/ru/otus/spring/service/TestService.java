package ru.otus.spring.service;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

public interface TestService {

    void executeTest() throws FileNotFoundException, URISyntaxException;
}
