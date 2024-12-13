package com.news_management.br.news_management.domain.usecases.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.news_management.br.news_management.domain.dtos.NewsAPIResponseDTO;
import com.news_management.br.news_management.domain.dtos.NewsItemSearchDTO;
import com.news_management.br.news_management.domain.models.NewsItem;
import com.news_management.br.news_management.domain.usecases.NewsSearchUseCase;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class NewsSearchUseCaseImpl implements NewsSearchUseCase {

    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @Override
    public Optional<NewsItem> findKeywordWithinTheIntroductionApiNews(List<NewsAPIResponseDTO> apiResponseDTOList, String keyword) {
        for (NewsAPIResponseDTO apiResponseDTO : apiResponseDTOList) {
            if (apiResponseDTO.getIntroducao() != null
            && apiResponseDTO.getIntroducao().toLowerCase().contains(keyword.toLowerCase())) {
                return Optional.of(toNewsItem(apiResponseDTO));
            }
        }
        return Optional.empty();
    }

    private NewsItem toNewsItem(NewsAPIResponseDTO newsAPIResponseDTO) {
        NewsItem newsItem = new NewsItem();
        newsItem.setText(newsAPIResponseDTO.getIntroducao());
        newsItem.setUrl(newsAPIResponseDTO.getLink());
        newsItem.setPublicationDate(LocalDate.parse(newsAPIResponseDTO.getData_publicacao().toString().trim(), dateFormat));

        return newsItem;
    }

}
