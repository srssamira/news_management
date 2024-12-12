package com.news_management.br.news_management.domain.dtos;

import java.util.List;

public class ApiReceivedDataDTO {
    private List<NewsAPIResponseDTO> items;

    public ApiReceivedDataDTO() {
    }

    public List<NewsAPIResponseDTO> getItems() {
        return items;
    }

    public void setItems(List<NewsAPIResponseDTO> items) {
        this.items = items;
    }
}
