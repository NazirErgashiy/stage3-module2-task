package com.mjc.school.controller.implementation;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.dto.AuthorDto;
import com.mjc.school.service.implementation.AuthorService;
import com.mjc.school.service.requests.AuthorRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthorController implements BaseController<AuthorRequest, AuthorDto, Long> {
    private final AuthorService SERVICE = new AuthorService();

    @Override
    public List<AuthorDto> readAll() {
        return SERVICE.readAll();
    }

    @Override
    public AuthorDto readById(Long id) {
        return SERVICE.readById(id);
    }

    @Override
    public AuthorDto create(AuthorRequest createRequest) {
        return SERVICE.create(createRequest);
    }

    @Override
    public AuthorDto update(AuthorRequest updateRequest) {
        return SERVICE.update(updateRequest);
    }

    @Override
    public boolean deleteById(Long id) {
        return SERVICE.deleteById(id);
    }
}
