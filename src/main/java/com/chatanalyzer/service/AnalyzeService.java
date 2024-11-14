package com.chatanalyzer.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.Time;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import com.chatanalyzer.dto.AnalyzerResponse;
import com.chatanalyzer.dto.ElasticResult;
import com.chatanalyzer.enums.ChatAggregations;
import com.chatanalyzer.enums.ChatField;
import com.chatanalyzer.enums.ElasticIndex;
import com.chatanalyzer.enums.TimeFormat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AnalyzeService {
    private final ElasticsearchClient elasticsearchClient;

    public AnalyzerResponse analyze() throws IOException {
        // 단톡방에서 말 많이 한 사람
        List<ElasticResult> talkatives = getTalkatives();

        // 많이 사용한 단어
        List<ElasticResult> popularWords = getPopularWords();

        // 말 많이 한 날
        List<ElasticResult> popularDays = getPopularDates();

        return new AnalyzerResponse(talkatives, popularWords, popularDays);
    }

    private List<ElasticResult> getTalkatives() throws IOException {
        SearchRequest searchRequest = SearchRequest.of(s -> s
                .index(ElasticIndex.MESSAGES.getValue())
                .size(0)
                .aggregations(ChatAggregations.TALKTATIVES.getValue(), a -> a
                        .terms(t -> t
                                .field(ChatField.SENDER.getValue())
                                .size(10)  // 상위 10개
                        )
                )
        );

        return elasticsearchClient.search(searchRequest, Object.class)
                .aggregations().get(ChatAggregations.TALKTATIVES.getValue()).sterms().buckets().array().stream()
                .map(ElasticResult::new).toList();
    }

    private List<ElasticResult> getPopularWords() throws IOException {
        SearchRequest searchRequest = SearchRequest.of(s -> s
                .index(ElasticIndex.MESSAGES.getValue())
                .size(0)
                .aggregations(ChatAggregations.POPULAR_WORDS.getValue(), a -> a
                        .terms(t -> t
                                .field(ChatField.CONTENT_ANALYZED.getValue())
                                .size(11)))
        );

        return elasticsearchClient.search(searchRequest, Object.class)
                .aggregations().get(ChatAggregations.POPULAR_WORDS.getValue()).sterms().buckets().array().stream()
                .map(ElasticResult::new).toList();
    }

    private List<ElasticResult> getPopularDates() throws IOException {
        SearchRequest searchRequest = SearchRequest.of(s -> s
                .index(ElasticIndex.MESSAGES.getValue())
                .size(0)
                .aggregations(ChatAggregations.POPULAR_WORDS.getValue(), a -> a
                        .dateHistogram(d -> d
                                .field(ChatField.SENT_AT.getValue())
                                .fixedInterval(Time.of(builder -> builder.time(TimeFormat.DAY.getValue())))
                                .format(TimeFormat.DAY.getFormat())
                                .minDocCount(1)
                        )
                        .aggregations(ChatAggregations.MESSAGE_COUNT.getValue(), ag -> ag
                                .valueCount(v -> v.field(ChatField.SENT_AT.getValue()))
                        )
                        .aggregations(ChatAggregations.TOP_DATES.getValue(), agr -> agr
                                .bucketSort(bs -> bs
                                        .sort(sort -> sort
                                                .field(f -> f
                                                        .field(ChatField.MESSAGE_COUNT.getValue())
                                                        .order(SortOrder.Desc)
                                                )
                                        )
                                        .size(10)
                                )
                        )
                )
        );

        return elasticsearchClient.search(searchRequest, Object.class)
                .aggregations().get(ChatAggregations.POPULAR_WORDS.getValue()).dateHistogram()
                .buckets().array().stream()
                .map(ElasticResult::new).toList();
    }
}
