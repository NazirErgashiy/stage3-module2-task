package com.mjc.school.controller.implementation;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.dto.NewsDto;
import com.mjc.school.service.implementation.NewsService;
import com.mjc.school.service.requests.NewsRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NewsController implements BaseController<NewsRequest, NewsDto, Long> {
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
    public NewsDto create(NewsRequest createRequest) {
        return SERVICE.create(createRequest);
    }

    @Override
    public NewsDto update(NewsRequest updateRequest) {
        return SERVICE.update(updateRequest);
    }

    @Override
    public boolean deleteById(Long id) {
        return SERVICE.deleteById(id);
    }
}
