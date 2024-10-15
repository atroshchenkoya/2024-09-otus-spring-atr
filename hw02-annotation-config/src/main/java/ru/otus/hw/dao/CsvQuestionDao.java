package ru.otus.hw.dao;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Repository
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
