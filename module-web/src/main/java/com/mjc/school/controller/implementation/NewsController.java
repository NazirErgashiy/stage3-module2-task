package com.mjc.school.controller.implementation;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.NewsControllerRequest;
import com.mjc.school.service.dto.NewsDto;
import com.mjc.school.service.implementation.NewsService;
import com.mjc.school.service.requests.NewsRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NewsController implements BaseController<NewsControllerRequest, NewsDto, Long> {
    private final NewsService SERVICE = new NewsService();

    @Override
    public List<NewsDto> readAll() {
        return SERVICE.readAll();
    }

    @Override
    public NewsDto readById(Long id) {
        return SERVICE.readById(id);
    }

    @Override
    public NewsDto create(NewsControllerRequest createRequest) {
        NewsRequest newsRequest = new NewsRequest();
        newsRequest.setTitle(createRequest.getTitle());
        newsRequest.setContent(createRequest.getContent());
        newsRequest.setAuthorId(createRequest.getAuthorId());
        return SERVICE.create(newsRequest);
    }

    @Override
    public NewsDto update(NewsControllerRequest updateRequest) {
        NewsRequest newsRequest = new NewsRequest();
        newsRequest.setTitle(updateRequest.getTitle());
        newsRequest.setContent(updateRequest.getContent());
        newsRequest.setAuthorId(updateRequest.getAuthorId());
        return SERVICE.update(newsRequest);
    }

    @Override
    public boolean deleteById(Long id) {
        return SERVICE.deleteById(id);
    }

    public void createTestDataBase() {
        SERVICE.createTestDB();
    }
}
