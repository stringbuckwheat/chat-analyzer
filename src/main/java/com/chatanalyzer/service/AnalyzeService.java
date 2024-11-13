package com.chatanalyzer.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.chatanalyzer.dto.AnalyzerResponse;
import com.chatanalyzer.dto.ElasticResult;
import com.chatanalyzer.enums.ChatAggregations;
import com.chatanalyzer.enums.ElasticIndex;
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

        return new AnalyzerResponse(talkatives, popularWords);
    }

    private List<ElasticResult> getTalkatives() throws IOException {
        SearchRequest searchRequest = SearchRequest.of(s -> s
                .index(ElasticIndex.MESSAGES.getValue())
                .size(0)
                .aggregations(ChatAggregations.TALKTATIVES.getValue(), a -> a
                        .terms(t -> t
                                .field("sender")  // sender로 집계
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
                                .field("content.analyzed")
                                .size(11)))
        );

        return elasticsearchClient.search(searchRequest, Object.class)
                .aggregations().get(ChatAggregations.POPULAR_WORDS.getValue()).sterms().buckets().array().stream()
                .map(ElasticResult::new).toList();
    }
}
