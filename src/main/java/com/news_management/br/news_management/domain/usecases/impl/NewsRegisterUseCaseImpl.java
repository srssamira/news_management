package com.news_management.br.news_management.domain.usecases.impl;

import com.news_management.br.news_management.domain.dtos.NewsRegisterDTO;
import com.news_management.br.news_management.domain.models.NewsItem;
import com.news_management.br.news_management.domain.usecases.NewsRegisterUseCase;
import java.time.LocalDate;

public class NewsRegisterUseCaseImpl implements NewsRegisterUseCase {
    private final NewsItem newsItemRepository;

    //injeçao de dependencia
    public NewsRegisterUseCaseImpl(NewsItem newsItem) {
        this.newsItemRepository = newsItem;
    }

    //registrar noticia
    public NewsItem registerNews(NewsRegisterDTO newsRegisterDTO) {

        //converter dto
        NewsItem newsItem = new NewsItem();
        newsItem.setUrl(newsRegisterDTO.getUrl());
        newsItem.setText(newsRegisterDTO.getText());
        newsItem.setPublicationDate(LocalDate.parse(newsRegisterDTO.getPublicationDate()));


    }
}

