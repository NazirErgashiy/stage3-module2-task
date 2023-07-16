package com.mjc.school.service.implementation;

import com.mjc.school.repository.implementation.dao.AuthorRepository;
import com.mjc.school.repository.implementation.dao.NewsRepository;
import com.mjc.school.repository.implementation.model.NewsModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.NewsDto;
import com.mjc.school.service.exceptions.AuthorNotFoundRuntimeException;
import com.mjc.school.service.exceptions.NewsNotFoundRuntimeException;
import com.mjc.school.service.implementation.validators.NewsValidator;
import com.mjc.school.service.mapper.NewsMapperImpl;
import com.mjc.school.service.requests.NewsRequest;

import java.util.ArrayList;
import java.util.List;

public class NewsService implements BaseService<NewsRequest, NewsDto, Long> {
    private final NewsRepository REPOSITORY = new NewsRepository();
    private final NewsMapperImpl MAPPER = new NewsMapperImpl();
    private final NewsValidator VALIDATOR = new NewsValidator();

    @Override
    public List<NewsDto> readAll() {
        return MAPPER.modelToDtoList(REPOSITORY.readAll());
    }

    public void saveAll(List<NewsDto> dto) {
        REPOSITORY.saveAll(MAPPER.dtoToModelList(dto));
    }

    @Override
    public NewsDto readById(Long id) {
        if (!REPOSITORY.existById(id)) {
            throw new NewsNotFoundRuntimeException("News with id [" + id + "] not found");
        }
        return MAPPER.modelToDto(REPOSITORY.readById(id).get());
    }

    @Override
    public NewsDto create(NewsRequest request) {
        if (VALIDATOR.validate(request)) {
            NewsDto dto = new NewsDto();
            dto.setId(getNewId());
            dto.setTitle(request.getTitle());
            dto.setContent(request.getContent());
            dto.setAuthorId(request.getAuthorId());
            return MAPPER.modelToDto(REPOSITORY.create(MAPPER.dtoToModel(dto)));
        }
        return null;
    }

    @Override
    public NewsDto update(NewsRequest request) {
        if (!REPOSITORY.existById(request.getId())) {
            throw new NewsNotFoundRuntimeException("News with id [" + request.getId() + "] not found");
        }
        AuthorRepository authorRepository = new AuthorRepository();
        if (!authorRepository.existById(request.getAuthorId())) {
            throw new AuthorNotFoundRuntimeException("Author with id [" + request.getAuthorId() + "] not found");
        }
        if (VALIDATOR.validate(request)) {
            NewsDto dto = new NewsDto();
            dto.setId(request.getId());
            dto.setTitle(request.getTitle());
            dto.setContent(request.getContent());
            dto.setAuthorId(request.getAuthorId());
            return MAPPER.modelToDto(REPOSITORY.update(MAPPER.dtoToModel(dto)));
        }
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return REPOSITORY.deleteById(id);
    }

    private long getNewId() {
        List<NewsModel> newsModels = new ArrayList<>(REPOSITORY.readAll());
        return newsModels.size() + 1;
    }

    public void deleteNewsWithoutAuthors(long id) {
        List<NewsDto> allNews = readAll();

        for (int i = 0; i < allNews.size(); i++) {
            if (allNews.get(i).getAuthorId() == id) {
                allNews.remove(i);
            }
        }
        saveAll(allNews);
    }
}
