package com.chatanalyzer.service;

import com.chatanalyzer.model.Message;
import com.chatanalyzer.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
@Slf4j
public class CsvService {
    private final MessageRepository messageRepository;

    public void save(String filePath) throws Exception {
        FileReader reader = new FileReader(Paths.get(filePath).toFile());
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader());

        for (CSVRecord record : csvParser) {
            Message message = new Message(
                    record.get("User"),
                    record.get("Message")
            );

            messageRepository.save(message);
        }

        csvParser.close();
    }
}
