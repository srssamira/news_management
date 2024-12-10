package com.news_management.br.news_management.domain.usecases.impl;

import com.news_management.br.news_management.domain.dtos.NewsVerificationRequestDTO;
import com.news_management.br.news_management.domain.dtos.NewsVerificationResponseDTO;
import com.news_management.br.news_management.domain.usecases.NewsVerificatoinUseCase;

public class NewsVerificationUseCaseImpl implements NewsVerificatoinUseCase {

    @Override
    public NewsVerificationResponseDTO verifyNews(NewsVerificationRequestDTO verificationRequest, Long id) {

       int score = 0;
       String classification;

        if (verificationRequest.isHaveCommunicationVehicle()) {
            score += 3;
        }

        if (verificationRequest.isHaveAuthor()) {
            score += 2;
        }

        if (verificationRequest.isHavePublicationDate()) {
            score += 1;
        }

        if (verificationRequest.isHaveTrustedSource()) {
            score += 5;
        } else {
            score -= 3;
        }

        if (verificationRequest.isHaveSensacionalistLanguage()) {
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

        return new NewsVerificationResponseDTO(classification,score, verificationRequest.getUrl());
    }
}
