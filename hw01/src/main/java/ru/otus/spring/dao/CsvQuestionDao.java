package ru.otus.spring.dao;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import ru.otus.spring.config.TestFileNameProvider;
import ru.otus.spring.dao.dto.QuestionDto;
import ru.otus.spring.domain.Question;
import ru.otus.spring.exceptions.QuestionReadException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@RequiredArgsConstructor
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
        if (inputStream == null) {
            throw new QuestionReadException("file not found!");
        }
        return inputStream;
    }
}
