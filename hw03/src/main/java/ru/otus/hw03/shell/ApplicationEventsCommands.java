package ru.otus.hw03.shell;

import lombok.RequiredArgsConstructor;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw03.service.TestRunnerService;

@ShellComponent(value = "Application Events Commands")
@RequiredArgsConstructor
public class ApplicationEventsCommands {

    private final TestRunnerService testRunnerService;

    @ShellMethod(value = "This command starts test!", key = {"g", "go"})
    public void go() {
        testRunnerService.run();
    }
}