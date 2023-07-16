package com.mjc.school.service.implementation.validators;

import com.mjc.school.repository.implementation.dao.AuthorRepository;
import com.mjc.school.repository.implementation.dao.NewsRepository;
import com.mjc.school.repository.implementation.model.NewsModel;
import com.mjc.school.service.Validator;
import com.mjc.school.service.dto.NewsDto;
import com.mjc.school.service.exceptions.AuthorNotFoundRuntimeException;
import com.mjc.school.service.exceptions.LengthRuntimeException;
import com.mjc.school.service.implementation.AuthorService;
import com.mjc.school.service.implementation.NewsService;
import com.mjc.school.service.requests.NewsRequest;

import java.util.List;

public class NewsValidator implements Validator<NewsRequest> {

    private final int TITLE_MIN_LENGTH = 5;
    private final int TITLE_MAX_LENGTH = 30;
    private final int CONTENT_MIN_LENGTH = 5;
    private final int CONTENT_MAX_LENGTH = 255;
    private final AuthorRepository AUTHOR_REPOSITORY = new AuthorRepository();

    @Override
    public Boolean validate(NewsRequest request) {
        if (AUTHOR_REPOSITORY.existById(request.getAuthorId())) {
            if (request.getTitle().length() < TITLE_MIN_LENGTH || request.getTitle().length() > TITLE_MAX_LENGTH) {
                throw new LengthRuntimeException("Title length is too small or too big");
            }
            if (request.getContent().length() < CONTENT_MIN_LENGTH || request.getContent().length() > CONTENT_MAX_LENGTH) {
                throw new LengthRuntimeException("Content length is too small or too big");
            }
            return true;
        }
        throw new AuthorNotFoundRuntimeException("Author with id: " + request.getAuthorId() + " not found");
    }
}
