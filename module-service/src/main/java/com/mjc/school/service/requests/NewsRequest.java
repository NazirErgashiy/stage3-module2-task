package com.mjc.school.service.requests;

import lombok.Data;

@Data
public class NewsRequest {
    private Long id;
    private String title;
    private String content;
    private Long authorId;
}
