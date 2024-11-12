package com.chatanalyzer.controller;

import com.chatanalyzer.dto.AnalyzerResponse;
import com.chatanalyzer.service.AnalyzeService;
import com.chatanalyzer.service.CsvService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class AnalyzerController {
    private final CsvService csvService;
    private final AnalyzeService analyzeService;

    @PostMapping("/api/messages")
    public ResponseEntity<Void> save(@RequestBody String filePath) throws Exception {
        csvService.save(filePath);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/analysis")
    public ResponseEntity<AnalyzerResponse> analyze() throws IOException {
        return ResponseEntity.ok(analyzeService.analyze());
    }
}
