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

    private void clearNewsRepo() {
        saveAll(new ArrayList<>());
    }

    public void createTestDB() {
        clearNewsRepo();

        createSpecialNews("Lord Of Dawn", "Now is the winter of our discontent", 1L);
        createSpecialNews("Heir Of The Ancients", "Made glorious summer by this sun of York;", 1L);
        createSpecialNews("Pirates Of The Ancestors", "And all the clouds that lour'd upon our house", 3L);
        createSpecialNews("Rebels Of Earth", "In the deep bosom of the ocean buried.", 4L);
        createSpecialNews("Kings And Mice", "Now are our brows bound with victorious wreaths;", 5L);
        createSpecialNews("Gods And Priests", "Our bruised arms hung up for monuments;", 6L);
        createSpecialNews("Goal Of The Lost Ones", "Our stern alarums changed to merry meetings,", 7L);
        createSpecialNews("End Of Nightmares", "Our dreadful marches to delightful measures.", 8L);
        createSpecialNews("Begging In The Future", "Grim-visaged war hath smooth'd his wrinkled front;", 9L);
        createSpecialNews("Flying Into The North", "I, that am curtail'd of this fair proportion,", 10L);
        createSpecialNews("Hero Of Dawn", "Creator Of Tomorrow", 11L);
        createSpecialNews("Human With Honor", "Priestess Without Desire", 12L);
        createSpecialNews("Strangers Of The Night", "Giants Of The Light", 13L);
        createSpecialNews("Officers With Vigor", "Boys Without Courage", 14L);
        createSpecialNews("Serpents And Companions", "Lords And Knights", 15L);
        createSpecialNews("Horses And Foreigners", "Wolves And Aliens", 16L);
        createSpecialNews("Cause Of Tomorrow", "Planet Of Water", 17L);
        createSpecialNews("Destiny Without Time", "Success Of The Void", 18L);
        createSpecialNews("Healing My Nightmares", "Clinging To The Future", 19L);
        createSpecialNews("Choking In Eternity", "Hurt By Nightmares", 20L);
    }

    private NewsDto createSpecialNews(String title, String content, Long authorId) {
        NewsRequest request1 = new NewsRequest();
        request1.setTitle(title);
        request1.setContent(content);
        request1.setAuthorId(authorId);
        return create(request1);
    }
}
