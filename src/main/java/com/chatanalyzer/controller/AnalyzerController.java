package com.chatanalyzer.controller;

import com.chatanalyzer.service.CsvService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AnalyzerController {
    private final CsvService csvService;

    @PostMapping("/api/messages")
    public ResponseEntity<Void> save(@RequestBody String filePath) throws Exception {
        csvService.save(filePath);
        return ResponseEntity.noContent().build();
    }
}
