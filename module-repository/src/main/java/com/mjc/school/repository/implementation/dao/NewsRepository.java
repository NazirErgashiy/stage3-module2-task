package com.mjc.school.repository.implementation.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.implementation.model.AuthorModel;
import com.mjc.school.repository.implementation.model.NewsModel;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class NewsRepository implements BaseRepository<NewsModel, Long> {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();//Jackson

    static {
        final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        OBJECT_MAPPER.setDateFormat(DATE_FORMAT);
        OBJECT_MAPPER.findAndRegisterModules();
    }

    @Override
    public List<NewsModel> readAll() {
        File file = getNewsFile();

        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            String str = new String(data, StandardCharsets.UTF_8);
            return Arrays.asList(OBJECT_MAPPER.readValue(str, NewsModel[].class));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void saveAll(List<NewsModel> models) {
        try (FileWriter fileWriter = new FileWriter(getNewsFile());
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            String json = OBJECT_MAPPER.writeValueAsString(models);
            bufferedWriter.write(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<NewsModel> readById(Long newsId) {
        List<NewsModel> allNews = readAll();


        for (int i = 0; i < allNews.size(); i++) {
            if (allNews.get(i).getId() == newsId) {
                return Optional.of(allNews.get(i));
            }
        }
        return Optional.empty();
    }

    @Override
    public NewsModel create(NewsModel model) {
        List<NewsModel> allNews;
        if (readAll() == null) {
            allNews = new ArrayList<>();
        } else {
            allNews = new ArrayList<>(readAll());
        }

        model.setCreateDate(nowIso8601());
        model.setLastUpdatedDate(nowIso8601());
        allNews.add(model);
        saveAll(allNews);
        Optional<NewsModel> optionalAuthorModel = readById(model.getId());
        if (optionalAuthorModel.isPresent()) {
            return optionalAuthorModel.get();
        }
        return new NewsModel();
    }

    @Override
    public NewsModel update(NewsModel model) {
        if (existById(model.getId())) {
            NewsModel model1 = readById(model.getId()).get();
            model1.setLastUpdatedDate(nowIso8601());
            model1.setTitle(model.getTitle());
            model1.setContent(model.getContent());
            model1.setAuthorId(model.getAuthorId());

            List<NewsModel> allNews = readAll();
            findObject(model1, allNews);
            saveAll(allNews);
            return readById(model1.getId()).get();
        }
        return null;
    }

    private LocalDateTime nowIso8601() {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        df.setTimeZone(tz);
        String nowAsISO = df.format(new Date());
        return LocalDateTime.parse(nowAsISO, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm'Z'"));
    }

    private File getNewsFile() {
        String correctPath = System.getProperty("user.dir");

        if (correctPath.contains("\\module-repository") || correctPath.contains("\\module-service")) {
            correctPath = correctPath.replace("\\module-repository", "");
            correctPath = correctPath.replace("\\module-service", "");
        }

        return new File(correctPath + "/module-repository/src/main/resources/news.txt");
    }

    private void findObject(NewsModel model, List<NewsModel> allNews) {
        for (int i = 0; 0 < allNews.size(); i++) {
            if (Objects.equals(allNews.get(i).getId(), model.getId())) {
                allNews.set(i, model);
                break;
            }
        }
    }

    @Override
    public boolean deleteById(Long id) {
        if (existById(id)) {
            List<NewsModel> allNews = new ArrayList<>(readAll());
            for (int i = 0; i < allNews.size(); i++) {
                if (Objects.equals(allNews.get(i).getId(), id)) {
                    allNews.remove(i);
                    saveAll(allNews);
                    break;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean existById(Long id) {
        List<NewsModel> allNews = readAll();
        for (NewsModel allNew : allNews) {
            if (Objects.equals(allNew.getId(), id)) return true;
        }
        return false;
    }
}
