package com.news_management.br.news_management.app.adapters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.news_management.br.news_management.app.infra.NewsItemRepository;
import com.news_management.br.news_management.app.infra.NewsNotFoundException;
import com.news_management.br.news_management.domain.dtos.NewsItemSearchDTO;
import com.news_management.br.news_management.domain.models.NewsItem;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NewsItemSearchAdapter {

    private final NewsItemRepository newsItemRepository;
    private final ExternalApiNewsAdapter externalApiNewsAdapter;

    private final ObjectMapper mapper;

    public NewsItemSearchAdapter(NewsItemRepository newsItemRepository, ExternalApiNewsAdapter externalApiNewsAdapter, ObjectMapper mapper) {
        this.newsItemRepository = newsItemRepository;
        this.externalApiNewsAdapter = externalApiNewsAdapter;
        this.mapper = mapper;
    }

    public NewsItemSearchDTO findByKeyword(String keyword) {

        Optional<NewsItem> newsItemOptional = newsItemRepository.findByTextContainingIgnoreCase(keyword);

        if (newsItemOptional.isPresent()) {
            return toNewsItemSearchDTO(newsItemOptional.get());
        }

        Optional<NewsItem> apiNewsItemOptional = externalApiNewsAdapter.findNewsItemByKeyword(keyword);

        if (apiNewsItemOptional.isPresent()) {
            NewsItem savedNewsItem = newsItemRepository.save(apiNewsItemOptional.get());
            return toNewsItemSearchDTO(savedNewsItem);
        }

        throw new NewsNotFoundException("newsItem not found");
    }


    private NewsItemSearchDTO toNewsItemSearchDTO(NewsItem newsItem) {
        return mapper.convertValue(newsItem, NewsItemSearchDTO.class);
    }
}
