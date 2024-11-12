package com.chatanalyzer.dto;

import co.elastic.clients.elasticsearch._types.aggregations.StringTermsBucket;
import lombok.Getter;

@Getter
public class Talkative {
    private String name;
    private Long count;

    public Talkative(StringTermsBucket bucket) {
        this.name = bucket.key().stringValue();
        this.count = bucket.docCount();
    }
}
