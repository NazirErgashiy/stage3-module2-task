package com.mjc.school.service.implementation;

import com.mjc.school.repository.implementation.dao.AuthorRepository;
import com.mjc.school.repository.implementation.model.AuthorModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.AuthorDto;
import com.mjc.school.service.exceptions.AuthorNotFoundRuntimeException;
import com.mjc.school.service.implementation.validators.AuthorValidator;
import com.mjc.school.service.mapper.AuthorMapperImpl;
import com.mjc.school.service.requests.AuthorRequest;

import java.util.ArrayList;
import java.util.List;

public class AuthorService implements BaseService<AuthorRequest, AuthorDto, Long> {
    private final AuthorRepository REPOSITORY = new AuthorRepository();
    private final AuthorMapperImpl MAPPER = new AuthorMapperImpl();
    private final AuthorValidator VALIDATOR = new AuthorValidator();
    private final NewsService NEWS_SERVICE = new NewsService();

    @Override
    public List<AuthorDto> readAll() {
        return MAPPER.modelToDtoList(REPOSITORY.readAll());
    }

    public void saveAll(List<AuthorDto> dto) {
        REPOSITORY.saveAll(MAPPER.dtoToModelList(dto));
    }

    @Override
    public AuthorDto readById(Long id) {
        if(!REPOSITORY.existById(id)){
            throw new AuthorNotFoundRuntimeException("Author with id ["+id+"] not found");
        }
        return MAPPER.modelToDto(REPOSITORY.readById(id).get());
    }

    @Override
    public AuthorDto create(AuthorRequest request) {
        if (VALIDATOR.validate(request)) {
            AuthorDto dto = new AuthorDto();
            dto.setId(getNewId());
            dto.setName(request.getName());
            return MAPPER.modelToDto(REPOSITORY.create(MAPPER.dtoToModel(dto)));
        }
        return null;
    }

    @Override
    public AuthorDto update(AuthorRequest request) {
        if (VALIDATOR.validate(request)) {
            AuthorDto dto = new AuthorDto();
            dto.setName(request.getName());
            return MAPPER.modelToDto(REPOSITORY.update(MAPPER.dtoToModel(dto)));
        }
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        NEWS_SERVICE.deleteNewsWithoutAuthors(id);
        return REPOSITORY.deleteById(id);
    }

    private long getNewId() {
        if (REPOSITORY.readAll() == null) {
            return 1;
        }
        List<AuthorModel> authorModels = new ArrayList<>(REPOSITORY.readAll());

        return authorModels.size() + 1;
    }
}
