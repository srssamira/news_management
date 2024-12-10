package com.news_management.br.news_management.domain.usecases;

import com.news_management.br.news_management.domain.models.NewsItem;

import java.util.Optional;

public interface NewsRegisterUseCase {

    //buscar id
    Optional<NewsItem> findById(Long id);

}
