package com.example.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostRequestDTO {
    private long id;
    private String name;
    private String content;
    private String created;
}
