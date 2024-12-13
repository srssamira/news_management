package com.news_management.br.news_management.app.adapters;

import com.news_management.br.news_management.domain.dtos.NewsRegisterDTO;
import com.news_management.br.news_management.domain.models.NewsItem;
import com.news_management.br.news_management.domain.usecases.NewsItemRepository;

public class NewsRegisterAdapter {


    private final NewsItemRepository newsItemRepository;

    public NewsRegisterAdapter(NewsItemRepository newsItemRepository) {
        this.newsItemRepository = newsItemRepository;
    }

    public Long registerNews(NewsRegisterDTO newsRegisterDTO) {

        NewsItem newsItem = new NewsItem();
        newsItem.setUrl(newsRegisterDTO.getUrl());
        newsItem.setText(newsRegisterDTO.getText());
        newsItem.setPublicationDate(newsRegisterDTO.getPublicationDate());



    private NewsItem newsItem;
    NewsItem savedNewsItem = newsItemRepository.save(newsItem);

     if(savedNewsItem ==null||savedNewsItem.getId()==null)

    {
        throw new NullPointerException("Falha ao salvar o item de notícia.");
    }

        return savedNewsItem.getId();
    }
}
