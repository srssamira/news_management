package com.news_management.br.news_management.app.ports;

import com.news_management.br.news_management.app.adapters.NewsVerificationAdapter;
import com.news_management.br.news_management.domain.dtos.NewsVerificationRequestDTO;
import com.news_management.br.news_management.domain.dtos.NewsVerificationResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/news")
@RestController
public class NewsVerificationController {

    private final NewsVerificationAdapter newsVerificationAdapter;

    @Autowired
    public NewsVerificationController(NewsVerificationAdapter newsVerificationAdapter) {
        this.newsVerificationAdapter = newsVerificationAdapter;
    }

    @PostMapping("/verification")
    public NewsVerificationResponseDTO verifyNews(@RequestBody NewsVerificationRequestDTO application, @RequestParam String url) {
        return newsVerificationAdapter.verifyNews(application, url);
    }
}
