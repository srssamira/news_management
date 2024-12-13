package com.news_management.br.news_management.domain.usecases;

import com.news_management.br.news_management.domain.dtos.NewsVerificationRequestDTO;
import com.news_management.br.news_management.domain.dtos.NewsVerificationResponseDTO;

public interface NewsVerificationUseCase {
    NewsVerificationResponseDTO verifyNews(NewsVerificationRequestDTO request, String url);

}
