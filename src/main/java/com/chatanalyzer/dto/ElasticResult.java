package com.chatanalyzer.dto;

import co.elastic.clients.elasticsearch._types.aggregations.StringTermsBucket;
import lombok.Getter;

@Getter
public class ElasticResult {
    private String title;
    private Long count;

    public ElasticResult(StringTermsBucket bucket) {
        this.title = bucket.key().stringValue();
        this.count = bucket.docCount();
    }
}
