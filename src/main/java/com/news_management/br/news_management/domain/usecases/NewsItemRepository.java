package com.news_management.br.news_management.domain.usecases;

import com.news_management.br.news_management.domain.models.NewsItem;

import java.util.Optional;

public interface NewsItemRepository {

    NewsItem save(NewsItem newsItem);

    Optional<NewsItem> findById(Long id);
}
