package snowcoach.Mapper;

import snowcoach.DTO.PostDTO;
import snowcoach.Model.Post;
import snowcoach.Model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PostMapper {

    private final UserMapper userMapper;

    public PostMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public PostDTO toDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setBody(post.getBody());
        postDTO.setMediaUrl(post.getMediaUrl());
        postDTO.setVisibility(post.getVisibility());
        postDTO.setUser(userMapper.toDTO(post.getUser())); // Map User to UserDTO
        postDTO.setTitle(post.getTitle());
        postDTO.setTopic(post.getTopic());
        postDTO.setLevel(post.getLevel());
        postDTO.setClaimer(userMapper.toDTO(post.getClaimer()));
        postDTO.setTimeCreated(post.getTimeCreated()); // Set timeCreated
        return postDTO;
    }

    public Post toEntity(PostDTO postDTO, User user) {
        Post post = new Post();
        post.setId(postDTO.getId());
        post.setBody(postDTO.getBody());
        post.setMediaUrl(postDTO.getMediaUrl());
        post.setVisibility(postDTO.getVisibility());
        post.setUser(user);
        post.setTitle(postDTO.getTitle());
        post.setTopic(postDTO.getTopic());
        post.setLevel(postDTO.getLevel());
        post.setClaimer(userMapper.toEntity(postDTO.getClaimer()));
        post.setTimeCreated(postDTO.getTimeCreated() != null ? postDTO.getTimeCreated() : LocalDateTime.now()); // Handle default
        return post;
    }
}