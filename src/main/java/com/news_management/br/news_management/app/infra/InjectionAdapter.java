package com.news_management.br.news_management.app.infra;

import com.news_management.br.news_management.domain.usecases.NewsSearchUseCase;
import com.news_management.br.news_management.domain.usecases.impl.NewsSearchUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class InjectionAdapter {

    @Bean
    public NewsSearchUseCase newsSearchUseCase() {
        return new NewsSearchUseCaseImpl();
    }
}
