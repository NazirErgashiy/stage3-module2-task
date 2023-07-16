package com.mjc.school.controller;

import lombok.Data;

@Data
public class NewsControllerRequest {
    private Long id;
    private String title;
    private String content;
    private Long authorId;
}
