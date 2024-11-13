package com.chatanalyzer.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ChatAggregations {
    TALKTATIVES("talkatives"),
    POPULAR_WORDS("popular_words");

    private String value;
}
