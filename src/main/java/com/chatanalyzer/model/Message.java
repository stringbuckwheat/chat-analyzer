package com.chatanalyzer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.time.LocalDateTime;

@Document(indexName = "messages")
@Mapping(mappingPath = "/elasticsearch/mappings/mappings.json")
@Setting(settingPath = "/elasticsearch/settings/settings.json")
public class Message {
    @Id
    private String id;

    private String sender;
    private String content;
    private LocalDateTime sentAt;

    public Message(String sender, String content, LocalDateTime sentAt) {
        this.sender = sender;
        this.content = content;
        this.sentAt = sentAt;
    }
}
