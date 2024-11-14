package com.chatanalyzer.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TimeFormat {
    DAY("1d", "yyyy-MM-dd"),
    HOUR("1h", "HH");

    private String value;
    private String format;
}
