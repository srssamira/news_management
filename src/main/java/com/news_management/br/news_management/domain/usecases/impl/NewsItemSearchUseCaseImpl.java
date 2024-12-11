package com.news_management.br.news_management.domain.usecases.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.news_management.br.news_management.app.adapters.ExternalApiNewsAdapter;
import com.news_management.br.news_management.app.infra.NewsItemRepository;
import com.news_management.br.news_management.domain.dtos.NewsItemSearchDTO;
import com.news_management.br.news_management.domain.models.NewsItem;
import com.news_management.br.news_management.domain.usecases.NewsItemSearchUseCase;

import java.util.Optional;

public class NewsItemSearchUseCaseImpl implements NewsItemSearchUseCase {

    private final NewsItemRepository newsItemRepository;
    private final ExternalApiNewsAdapter externalApiNewsAdapter;
    private ObjectMapper mapper;

    public NewsItemSearchUseCaseImpl(NewsItemRepository newsItemRepository, ExternalApiNewsAdapter externalApiNewsAdapter) {
        this.newsItemRepository = newsItemRepository;
        this.externalApiNewsAdapter = externalApiNewsAdapter;
    }


    @Override
    public Optional<NewsItemSearchDTO> findByKeyword(String keyword) {
        Optional<NewsItem> newsItemOptional = newsItemRepository.findByKeyword(keyword);

        if (newsItemOptional.isPresent()) {
            return Optional.of(toNewsItemSearchDTO(newsItemOptional.get()));
        }

        if (newsItemOptional.isEmpty()) {
            Optional<NewsItem> apiNewsItemOptional = externalApiNewsAdapter.findNewsItemByKeyword(keyword);

            if (apiNewsItemOptional.isPresent()) {
                NewsItem savedNewsItem = newsItemRepository.save(apiNewsItemOptional.get());
                return Optional.of(toNewsItemSearchDTO(savedNewsItem));
            } else return Optional.empty();

        }

        return Optional.empty();
    }


    private NewsItemSearchDTO toNewsItemSearchDTO(NewsItem newsItem) {
        return mapper.convertValue(newsItem, NewsItemSearchDTO.class);
    }
}
