package com.news_management.br.news_management.domain.usecases.impl;

import com.news_management.br.news_management.domain.dtos.NewsVerificationRequestDTO;
import com.news_management.br.news_management.domain.dtos.NewsVerificationResponseDTO;
import com.news_management.br.news_management.domain.usecases.NewsVerificatoinUseCase;

public class NewsVerificationUseCaseImpl implements NewsVerificatoinUseCase {
    public NewsVerificationResponseDTO verifyNews(NewsVerificationRequestDTO request, String url) {
        int score = 0;
        String classification;

        if (request.isHaveCommunicationVehicle()) {
            score += 3;
        }

        if (request.isHaveAuthor()) {
            score += 2;
        }

        if (request.isHavePublicationDate()) {
            score += 1;
        }

        if (request.isHaveTrustedSource()) {
            score += 5;
        } else {
            score -= 3;
        }

        if (request.isHaveSensacionalistLanguage()) {
            score -= 3;
        }

        if (score >= 8) {
            classification = "HIGH CONFIDENCE";
        } else if (score >= 3) {
            classification = "MEDIUM CONFIDENCE";
        } else if (score >= -2) {
            classification = "LOW CONFIDENCE";
        } else {
            classification = "HIGH SUSPICION";
        }

        NewsVerificationResponseDTO answer = new NewsVerificationResponseDTO();
        answer.setClassification(classification);
        answer.setScore(score);
        answer.setUrl(url);

        return answer;
    }
}
