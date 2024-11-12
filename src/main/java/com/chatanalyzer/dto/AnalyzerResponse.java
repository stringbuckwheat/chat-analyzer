package com.chatanalyzer.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class AnalyzerResponse {
    private List<Talkative> talkatives;

    public AnalyzerResponse(List<Talkative> talkatives) {
        this.talkatives = talkatives;
    }
}
