package ru.otus.hw03.dao;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.bean.CsvToBeanBuilder;
import ru.otus.hw03.config.TestFileNameProvider;
import ru.otus.hw03.dao.dto.QuestionDto;
import ru.otus.hw03.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw03.exceptions.QuestionReadException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {

        try (InputStream inputStream = getInputStream();
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream)) {

            CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();
            List<QuestionDto> questionDtoList = new CsvToBeanBuilder<QuestionDto>(inputStreamReader)
                    .withSkipLines(1)
                    .withSeparator(csvParser.getSeparator())
                    .withType(QuestionDto.class)
                    .build()
                    .parse();
            return questionDtoList.stream().map(QuestionDto::toDomainObject).toList();

        } catch (IOException e) {
            throw new QuestionReadException("Cant read from file!");
        }
    }

    private InputStream getInputStream() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileNameProvider.getTestFileName());
        if (Objects.isNull(inputStream)) {
            throw new QuestionReadException("file not found!");
        }
        return inputStream;
    }
}
