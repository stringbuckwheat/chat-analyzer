package com.chatanalyzer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AnalyzerResponse {
    private List<ElasticResult> talkatives;
    private List<ElasticResult> popularWords;
    private List<ElasticResult> popularDays;
}
