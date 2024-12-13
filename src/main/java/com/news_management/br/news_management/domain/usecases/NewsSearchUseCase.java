package com.news_management.br.news_management.domain.usecases;

import com.news_management.br.news_management.domain.dtos.NewsAPIResponseDTO;
import com.news_management.br.news_management.domain.models.NewsItem;

import java.util.List;
import java.util.Optional;

public interface NewsSearchUseCase {

    Optional<NewsItem> findKeywordWithinTheIntroductionApiNews(List<NewsAPIResponseDTO> apiResponseDTOList, String keyword);
}
