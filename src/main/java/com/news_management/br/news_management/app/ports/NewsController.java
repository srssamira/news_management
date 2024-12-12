package com.news_management.br.news_management.app.ports;

import com.news_management.br.news_management.app.adapters.NewsItemSearchAdapter;
import com.news_management.br.news_management.app.infra.NewsNotFoundException;
import com.news_management.br.news_management.domain.dtos.NewsItemSearchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    NewsItemSearchAdapter newsItemSearchAdapter;

    @GetMapping("/search")
    public ResponseEntity<?> searchNews(@RequestParam("keyword") String keyword) {
        try {
            NewsItemSearchDTO newsItemSearchDTO = newsItemSearchAdapter.findByKeyword(keyword);
            return ResponseEntity.ok(newsItemSearchDTO);
        } catch (NewsNotFoundException notFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "news not found"));
        }
    }
}
