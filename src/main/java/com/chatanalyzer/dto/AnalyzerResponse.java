package com.chatanalyzer.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class AnalyzerResponse {
    private List<ElasticResult> talkatives;
    private List<ElasticResult> popularWords;

    public AnalyzerResponse(List<ElasticResult> talkatives, List<ElasticResult> popularWords) {
        this.talkatives = talkatives;
        this.popularWords = popularWords;
    }
}
