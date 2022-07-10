package com.example.springboot.manager;

import com.example.springboot.dto.PostRequestDTO;
import com.example.springboot.dto.PostResponseDTO;
import com.example.springboot.dto.UserRequestDTO;
import com.example.springboot.dto.UserResponseDTO;
import com.example.springboot.entity.PostEntity;
import com.example.springboot.entity.UserEntity;
import com.example.springboot.exception.UserNotFoundException;
import com.example.springboot.repository.PostRepository;
import com.example.springboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Transactional
@RequiredArgsConstructor
public class PostManager {
    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;
    private final NamedParameterJdbcOperations jdbcOperations;
    private final RowMapper<UserResponseDTO> rowMapper = (rs, rowNum) ->
            new UserResponseDTO(rs.getLong("id"), rs.getString("name"));
    private final Function<PostEntity, PostResponseDTO> postEntityToUserResponseDTO = postEntity -> new PostResponseDTO(postEntity.getId(), postEntity.getName(), postEntity.getContent(), postEntity.getCreated());

    public List<PostResponseDTO> getAll() {
        return postRepository.findAll().stream()
                .map(postEntityToUserResponseDTO)
                .collect(Collectors.toList());
    }

    public PostResponseDTO getById(final long id) {
        return postRepository.findById(id)
                .map(postEntityToUserResponseDTO)
                .orElseThrow(UserNotFoundException::new);
    }

    public PostResponseDTO create(final PostRequestDTO requestDTO) {
        PostEntity postEntity = new PostEntity(0, requestDTO.getName(), requestDTO.getContent(), requestDTO.getCreated());
        PostEntity savedEntity = postRepository.save(postEntity);
        return postEntityToUserResponseDTO.apply(savedEntity);
    }

    public PostResponseDTO update(final PostRequestDTO requestDTO) {
        PostEntity postEntity = postRepository.getReferenceById(requestDTO.getId());
        postEntity.setName(requestDTO.getName());
        postEntity.setContent(requestDTO.getContent());
        postEntity.setCreated(requestDTO.getCreated());
        return postEntityToUserResponseDTO.apply(postEntity);
    }

    public void deleteById(final long id) {
        postRepository.deleteById(id);
    }
}
