package ru.otus.spring.dao;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import ru.otus.spring.config.TestFileNameProvider;
import ru.otus.spring.dao.dto.QuestionDto;
import ru.otus.spring.domain.Question;
import ru.otus.spring.exceptions.QuestionReadException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.List;

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {

    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {

        File b = getFile();

        CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();
        List<QuestionDto> questionDtos;
        try {
            questionDtos = new CsvToBeanBuilder(new FileReader(b))
                    .withSkipLines(1)
                    .withSeparator(csvParser.getSeparator())
                    .withType(QuestionDto.class)
                    .build()
                    .parse();
        } catch (FileNotFoundException e) {
            throw new QuestionReadException("Can't parse file!");
        }
        return questionDtos.stream().map(QuestionDto::toDomainObject).toList();
    }

    private File getFile() {
        URL resource = getClass().getClassLoader().getResource(fileNameProvider.getTestFileName());
        File file;
        if (resource == null) {
            throw new QuestionReadException("file not found!");
        } else {
            try {
                file = new File(resource.toURI());
            } catch (Exception e) {
                throw new QuestionReadException("Can't read file!");
            }
        }
        return file;
    }
}
