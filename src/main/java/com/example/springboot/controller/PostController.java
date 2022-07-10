package com.example.springboot.controller;

import com.example.springboot.dto.PostRequestDTO;
import com.example.springboot.dto.PostResponseDTO;
import com.example.springboot.dto.UserRequestDTO;
import com.example.springboot.dto.UserResponseDTO;
import com.example.springboot.manager.PostManager;
import com.example.springboot.manager.UserManager;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.Manager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor // генерирует конструктор только для final non-static полей
public class PostController {
    private final PostManager manager;

    @GetMapping("/posts")
    public List<PostResponseDTO> getAll() {
        final List<PostResponseDTO> responseDTO = manager.getAll();
        return responseDTO;
    }

    // TODO: http://localhost:8080/users/1
    @GetMapping("/posts/{id}")
    public PostResponseDTO getById(@PathVariable final long id) {
        final PostResponseDTO responseDTO = manager.getById(id);
        return responseDTO;
    }

    @PostMapping("/posts")
    public PostResponseDTO create(@RequestBody final PostRequestDTO requestDTO) {
        final PostResponseDTO responseDTO = manager.create(requestDTO);
        return responseDTO;
    }

    @PutMapping("/posts")
    public PostResponseDTO update(@RequestBody final PostRequestDTO requestDTO) {
        final PostResponseDTO responseDTO = manager.update(requestDTO);
        return responseDTO;
    }

    @DeleteMapping("/posts/{id}")
    public void deleteById(@PathVariable final long id) {
        manager.deleteById(id);
    }
}

