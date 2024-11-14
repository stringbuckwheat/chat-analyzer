package com.chatanalyzer.dto;

import co.elastic.clients.elasticsearch._types.aggregations.DateHistogramBucket;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsBucket;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ElasticResult {
    private String title;
    private Long count;

    public ElasticResult(StringTermsBucket bucket) {
        this.title = bucket.key().stringValue();
        this.count = bucket.docCount();
    }

    public ElasticResult(DateHistogramBucket bucket) {
        this.title = bucket.keyAsString();
        this.count = bucket.docCount();
    }
}
