package com.news_management.br.news_management.app.ports;

import com.news_management.br.news_management.app.adapters.NewsRegisterAdapter;
import com.news_management.br.news_management.domain.dtos.NewsRegisterDTO;
import com.news_management.br.news_management.domain.models.NewsItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/news")
public class NewsRegisterController {

    private final NewsRegisterAdapter newsRegisterAdapter;

    public NewsRegisterController(NewsRegisterAdapter newsRegisterAdapter) {
        this.newsRegisterAdapter = newsRegisterAdapter;
    }

    @PostMapping
    public ResponseEntity<?> registerNews(@RequestBody NewsRegisterDTO newsRegisterDTO) {
        try {

            NewsItem registeredNews = newsRegisterAdapter.registerNews(newsRegisterDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(registeredNews);

        } catch (IllegalArgumentException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: Invalid news data. " + e.getMessage());
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error in registering the news. " + e.getMessage());
        }
    }
}
