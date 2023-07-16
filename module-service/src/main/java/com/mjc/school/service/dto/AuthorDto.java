package com.mjc.school.service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuthorDto {
    private Long id;
    private String name;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdatedDate;
}
