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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CsvService {
    private final MessageRepository messageRepository;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final int BATCH_SIZE = 500;


    public void save(String filePath) throws Exception {
        FileReader reader = new FileReader(Paths.get(filePath).toFile());
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader());

        List<Message> batchMessages = new ArrayList<>();

        for (CSVRecord record : csvParser) {
            Message message = new Message(
                    record.get("User"),
                    record.get("Message"),
                    LocalDateTime.parse(record.get(0), DATE_FORMATTER)
            );

            batchMessages.add(message);

            if(batchMessages.size() >= BATCH_SIZE) {
                messageRepository.saveAll(batchMessages);
                batchMessages.clear();
            }
        }

        if(!batchMessages.isEmpty()) {
            messageRepository.saveAll(batchMessages);
        }

        csvParser.close();
    }
}
