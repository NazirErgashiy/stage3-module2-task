package com.mjc.school.service.implementation;

import com.mjc.school.service.dto.AuthorDto;
import com.mjc.school.service.dto.NewsDto;
import com.mjc.school.service.exceptions.AuthorNotFoundRuntimeException;
import com.mjc.school.service.exceptions.LengthRuntimeException;
import com.mjc.school.service.exceptions.NewsNotFoundRuntimeException;
import com.mjc.school.service.mapper.NewsMapperImpl;
import com.mjc.school.service.requests.AuthorRequest;
import com.mjc.school.service.requests.NewsRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Component
class NewsServiceTest {

    /*
    @Autowired
    public NewsServiceTest(NewsService newsService, AuthorService authorService) {
        NEWS_SERVICE = newsService;
        AUTHOR_SERVICE = authorService;
    }

    private NewsService NEWS_SERVICE;
    private AuthorService AUTHOR_SERVICE;

    private void clearAuthorRepo() {
        AUTHOR_SERVICE.saveAll(new ArrayList<>());
    }

    private void clearNewsRepo() {
        NEWS_SERVICE.saveAll(new ArrayList<>());
    }

    private NewsDto createBasicNews() {
        NewsRequest request1 = new NewsRequest();
        request1.setTitle("Title");
        request1.setContent("Abobus & Amogus");
        request1.setAuthorId(1L);
        return NEWS_SERVICE.create(request1);
    }

    private AuthorDto createBasicAuthor() {
        AuthorRequest aReq1 = new AuthorRequest();
        aReq1.setName("Aboba");
        return AUTHOR_SERVICE.create(aReq1);
    }

    @Test
    void saveAll() {
        clearAuthorRepo();
        clearNewsRepo();
        AuthorDto authorDto1 = createBasicAuthor();
        NewsDto newsDto1 = createBasicNews();

        List<NewsDto> allNews = NEWS_SERVICE.readAll();
        List<AuthorDto> allAuthors = AUTHOR_SERVICE.readAll();
        assertEquals(newsDto1, allNews.get(0));
        assertEquals(authorDto1, allAuthors.get(0));
    }

    @Test
    void readById() {
        clearAuthorRepo();
        clearNewsRepo();
        AuthorDto authorDto1 = createBasicAuthor();
        NewsDto newsDto1 = createBasicNews();

        assertEquals(newsDto1, NEWS_SERVICE.readById(1L));
        assertEquals(authorDto1, AUTHOR_SERVICE.readById(1L));

        assertThrows(NewsNotFoundRuntimeException.class, () -> {
            NEWS_SERVICE.readById(5L);
        });
        assertThrows(AuthorNotFoundRuntimeException.class, () -> {
            AUTHOR_SERVICE.readById(5L);
        });
    }

    @Test
    void create1() {
        clearAuthorRepo();
        clearNewsRepo();

        NewsRequest request1 = new NewsRequest();
        request1.setTitle("Title");
        request1.setContent("Abobus & Amogus");
        request1.setAuthorId(5L);
        assertThrows(AuthorNotFoundRuntimeException.class, () -> {
            NEWS_SERVICE.create(request1);
        });
    }

    @Test
    void create2() {
        clearAuthorRepo();
        clearNewsRepo();
        createBasicAuthor();

        NewsRequest request1 = new NewsRequest();
        request1.setTitle("");
        request1.setContent("");
        request1.setAuthorId(1L);
        assertThrows(LengthRuntimeException.class, () -> {
            NEWS_SERVICE.create(request1);
        });
    }

    @Test
    void update1() {
        clearAuthorRepo();
        clearNewsRepo();
        createBasicAuthor();
        createBasicNews();

        NewsRequest request1 = new NewsRequest();
        request1.setId(1L);
        request1.setTitle("New Title");
        request1.setContent("New Content");
        request1.setAuthorId(1L);

        assertEquals(request1.getTitle(), NEWS_SERVICE.update(request1).getTitle());
        assertEquals(request1.getContent(), NEWS_SERVICE.update(request1).getContent());
        assertEquals(request1.getAuthorId(), NEWS_SERVICE.update(request1).getAuthorId());
    }

    @Test
    void update2() {
        clearAuthorRepo();
        clearNewsRepo();
        createBasicAuthor();
        createBasicNews();

        NewsRequest request1 = new NewsRequest();
        request1.setId(2L);
        request1.setTitle("New Title");
        request1.setContent("New Content");
        request1.setAuthorId(1L);

        assertThrows(NewsNotFoundRuntimeException.class, () -> {
            NEWS_SERVICE.update(request1);
        });

        NewsRequest request2 = new NewsRequest();
        request2.setId(1L);
        request2.setTitle("New Title");
        request2.setContent("New Content");
        request2.setAuthorId(2L);

        assertThrows(AuthorNotFoundRuntimeException.class, () -> {
            NEWS_SERVICE.update(request2);
        });
    }

    @Test
    void deleteById() {
        clearAuthorRepo();
        clearNewsRepo();
        createBasicAuthor();
        createBasicNews();

        NEWS_SERVICE.deleteById(1L);
        assertThrows(NewsNotFoundRuntimeException.class, () -> {
            NEWS_SERVICE.readById(1L);
        });
        AUTHOR_SERVICE.deleteById(1L);
        assertThrows(AuthorNotFoundRuntimeException.class, () -> {
            AUTHOR_SERVICE.readById(1L);
        });
    }

    @Test
    void deleteById1() {
        clearAuthorRepo();
        clearNewsRepo();
        createBasicAuthor();
        createBasicNews();

        AUTHOR_SERVICE.deleteById(1L);
        assertThrows(AuthorNotFoundRuntimeException.class, () -> {
            AUTHOR_SERVICE.readById(1L);
        });
        assertThrows(NewsNotFoundRuntimeException.class, () -> {
            NEWS_SERVICE.readById(1L);
        });
    }

    @Test
    void deleteById2() {
        clearAuthorRepo();
        clearNewsRepo();

        assertFalse(AUTHOR_SERVICE.deleteById(1L));
        assertFalse(NEWS_SERVICE.deleteById(1L));
    }


    public void createTestDB() {
        clearAuthorRepo();
        clearNewsRepo();

        createSpecialAuthor("Laila Mcmahon");
        createSpecialAuthor("Kaleb Proctor");
        createSpecialAuthor("Livia Moody");
        createSpecialAuthor("Corey Terry");
        createSpecialAuthor("Charlie West");
        createSpecialAuthor("Cleo Rush");
        createSpecialAuthor("Kamran Wolf");
        createSpecialAuthor("Elissa Swee");
        createSpecialAuthor("Alys Hines");
        createSpecialAuthor("Tiffany Ber");
        createSpecialAuthor("Lowri Ortiz");
        createSpecialAuthor("Kelsey Gal");
        createSpecialAuthor("Darren Salas");
        createSpecialAuthor("Jeremy Eaton");
        createSpecialAuthor("Simon Summers");
        createSpecialAuthor("Julian Gibbons");
        createSpecialAuthor("Nellie Poole");
        createSpecialAuthor("Hazel Murray");
        createSpecialAuthor("Cohen Holmes");
        createSpecialAuthor("Serena Buch");

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
        return NEWS_SERVICE.create(request1);
    }

    private AuthorDto createSpecialAuthor(String name) {
        AuthorRequest aReq1 = new AuthorRequest();
        aReq1.setName(name);
        return AUTHOR_SERVICE.create(aReq1);
    }
     */
}