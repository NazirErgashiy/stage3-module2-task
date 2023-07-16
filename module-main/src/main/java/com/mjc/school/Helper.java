package com.mjc.school;

import com.mjc.school.service.dto.AuthorDto;
import com.mjc.school.service.dto.NewsDto;
import com.mjc.school.service.implementation.AuthorService;
import com.mjc.school.service.implementation.NewsService;
import com.mjc.school.service.requests.AuthorRequest;
import com.mjc.school.service.requests.NewsRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class Helper {

    private final NewsService NEWS_SERVICE = new NewsService();
    private final AuthorService AUTHOR_SERVICE = new AuthorService();

    private void clearAuthorRepo() {
        AUTHOR_SERVICE.saveAll(new ArrayList<>());
    }

    private void clearNewsRepo() {
        NEWS_SERVICE.saveAll(new ArrayList<>());
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
}
