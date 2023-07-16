package com.mjc.school.repository.implementation.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.implementation.model.AuthorModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class AuthorRepository implements BaseRepository<AuthorModel, Long> {
    private static ObjectMapper OBJECT_MAPPER =new ObjectMapper();

    static {
        final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        OBJECT_MAPPER.setDateFormat(DATE_FORMAT);
        OBJECT_MAPPER.findAndRegisterModules();
    }

    @Override
    public List<AuthorModel> readAll() {
        File file = getAuthorFile();

        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            String str = new String(data, StandardCharsets.UTF_8);
            return Arrays.asList(OBJECT_MAPPER.readValue(str, AuthorModel[].class));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void saveAll(List<AuthorModel> models) {
        try (FileWriter fileWriter = new FileWriter(getAuthorFile());
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            String json = OBJECT_MAPPER.writeValueAsString(models);
            bufferedWriter.write(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<AuthorModel> readById(Long newsId) {
        List<AuthorModel> allNews = readAll();

        for (int i = 0; i < allNews.size(); i++) {
            if (allNews.get(i).getId() == newsId) return Optional.ofNullable(allNews.get(i));
        }
        return Optional.empty();
    }

    @Override
    public AuthorModel create(AuthorModel model) {
        List<AuthorModel> allAuthors;
        if (readAll() == null) {
            allAuthors = new ArrayList<>();
        } else {
            allAuthors = new ArrayList<>(readAll());
        }

        model.setCreateDate(nowIso8601());
        model.setLastUpdatedDate(nowIso8601());
        allAuthors.add(model);
        saveAll(allAuthors);
        Optional<AuthorModel> optionalAuthorModel = readById(model.getId());
        if (optionalAuthorModel.isPresent()) {
            return optionalAuthorModel.get();
        }
        return new AuthorModel();
    }

    @Override
    public AuthorModel update(AuthorModel model) {
        if (existById(model.getId())) {
            List<AuthorModel> allAuthors = readAll();
            findObject(model, allAuthors);
            model.setLastUpdatedDate(nowIso8601());
            saveAll(allAuthors);
            return readById(model.getId()).get();
        }
        return null;
    }

    private File getAuthorFile() {
        String correctPath = System.getProperty("user.dir");

        if (correctPath.contains("\\module-repository") || correctPath.contains("\\module-service")) {
            correctPath = correctPath.replace("\\module-repository", "");
            correctPath = correctPath.replace("\\module-service", "");

        }
        return new File(correctPath + "/module-repository/src/main/resources/author.txt");
    }

    private void findObject(AuthorModel model, List<AuthorModel> allAuthors) {
        for (int i = 0; 0 < allAuthors.size(); i++) {
            if (Objects.equals(allAuthors.get(i).getId(), model.getId())) {
                allAuthors.set(i, model);
                break;
            }
        }
    }

    private LocalDateTime nowIso8601() {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        df.setTimeZone(tz);
        String nowAsISO = df.format(new Date());
        return LocalDateTime.parse(nowAsISO, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm'Z'"));
    }

    @Override
    public boolean deleteById(Long id) {
        if (existById(id)) {
            List<AuthorModel> allNews = new ArrayList<>(readAll());
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
        List<AuthorModel> allAuthors = readAll();
        for (AuthorModel author : allAuthors) {
            if (Objects.equals(author.getId(), id)) return true;
        }
        return false;
    }

    //TODO ЕСЛИ УДАЛИЛИ АВТОРА ТО ВСЕ ЕМУ ПРИВЯЗАННЫЕ НОВОСТИ ТОЖЕ СНЕСУТЬСЯ
}
