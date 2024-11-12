package com.chatanalyzer.repository;


import com.chatanalyzer.model.Message;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface MessageRepository extends ElasticsearchRepository<Message, String> {
}
