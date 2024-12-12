package com.news_management.br.news_management.app.infra;

import com.news_management.br.news_management.domain.models.NewsItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewsItemRepository extends JpaRepository<NewsItem, Long> {
    Optional<NewsItem> findByTextContainingIgnoreCase(String keyword);
}
