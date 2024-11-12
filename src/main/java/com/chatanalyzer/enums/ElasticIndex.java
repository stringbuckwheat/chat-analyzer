package com.chatanalyzer.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ElasticIndex {
    MESSAGES("messages");

    private String value;
}
