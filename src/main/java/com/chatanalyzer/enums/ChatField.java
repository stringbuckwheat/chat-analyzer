package com.chatanalyzer.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ChatField {
    SENDER("sender"),
    CONTENT_ANALYZED("content.analyzed"),
    SENT_AT("sentAt"),

    MESSAGE_COUNT("message_count");

    private String value;
}
