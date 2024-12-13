package com.news_management.br.news_management.app.infra;
import com.news_management.br.news_management.domain.usecases.NewsVerificationUseCase;
import com.news_management.br.news_management.domain.usecases.impl.NewsVerificationUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {
    @Bean
    public NewsVerificationUseCase newsVerificationUseCase() {
        return new NewsVerificationUseCaseImpl();
    }
}
