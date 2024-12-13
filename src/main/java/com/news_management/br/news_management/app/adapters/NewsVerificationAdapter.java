package com.news_management.br.news_management.app.adapters;

import com.news_management.br.news_management.domain.dtos.NewsVerificationRequestDTO;
import com.news_management.br.news_management.domain.dtos.NewsVerificationResponseDTO;
import com.news_management.br.news_management.domain.usecases.NewsVerificationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NewsVerificationAdapter {
    private final NewsVerificationUseCase newsVerificationUseCase;

    @Autowired
    public NewsVerificationAdapter(NewsVerificationUseCase newsVerificationUseCase) {
        this.newsVerificationUseCase = newsVerificationUseCase;
    }

    public NewsVerificationResponseDTO verifyNews(NewsVerificationRequestDTO request, String url) {
        return newsVerificationUseCase.verifyNews(request, url);
    }
}
