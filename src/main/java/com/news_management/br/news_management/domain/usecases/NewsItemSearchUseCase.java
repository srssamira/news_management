package com.news_management.br.news_management.domain.usecases;

import com.news_management.br.news_management.domain.dtos.NewsItemSearchDTO;
import com.news_management.br.news_management.domain.models.NewsItem;

import java.util.Optional;

public interface NewsItemSearchUseCase {

    Optional<NewsItemSearchDTO> findByKeyword(String keyword);

}
