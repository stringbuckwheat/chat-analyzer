package com.chatanalyzer.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ChatAggregations {
    TALKTATIVES("talkatives"),
    POPULAR_WORDS("popular_words"),
    POPULAR_DATES("popular_dates"),
    MESSAGE_COUNT("message_count"),
    TOP_DATES("top_dates"),
    AVERAGE_BY_HOUR("average_by_hour"),
    AVERAGE("average"),
    COUNT("count");

    private String value;
}
