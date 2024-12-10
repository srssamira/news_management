package com.news_management.br.news_management.domain.repositories;

import com.news_management.br.news_management.domain.models.NewsItem;

import java.util.Optional;

public interface NewsItemRepository {
    Optional<NewsItem> findByKeyword(String keyword);
}
