package com.news_management.br.news_management.domain.usecases.impl;

import com.news_management.br.news_management.domain.dtos.NewsVerificationRequestDTO;
import com.news_management.br.news_management.domain.dtos.NewsVerificationResponseDTO;
import com.news_management.br.news_management.domain.usecases.NewsVerificationUseCase;

public class NewsVerificationUseCaseImpl implements NewsVerificationUseCase {

    @Override
    public NewsVerificationResponseDTO verifyNews(NewsVerificationRequestDTO request, String url) {
        int score = calculateScore(request);
        String classification = finalClassification(score);

        NewsVerificationResponseDTO answer = new NewsVerificationResponseDTO();
        answer.setClassification(classification);
        answer.setScore(score);
        answer.setUrl(url);

        return answer;
    }

    private int calculateScore(NewsVerificationRequestDTO request) {
        int score = 0;

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

        return score;
    }

    private String finalClassification(int score) {
        if (score >= 8) {
            return "HIGH CONFIDENCE";
        } else if (score >= 3) {
            return "MEDIUM CONFIDENCE";
        } else if (score >= -2) {
            return "LOW CONFIDENCE";
        } else {
            return "HIGH SUSPICION";
        }
    }
}
