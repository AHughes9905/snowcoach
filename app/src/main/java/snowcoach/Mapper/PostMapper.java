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
        postDTO.setUser(post.getUser().getUsername());
        postDTO.setTitle(post.getTitle());
        postDTO.setTopic(post.getTopic());
        postDTO.setLevel(post.getLevel());
        postDTO.setClaimer(post.getClaimer().getUsername() != null ? post.getClaimer().getUsername() : null);
        postDTO.setTimeCreated(post.getTimeCreated()); // Set timeCreated
        return postDTO;
    }

    public Post toEntity(PostDTO postDTO) {
        Post post = new Post();
        post.setId(postDTO.getId());
        post.setBody(postDTO.getBody());
        post.setMediaUrl(postDTO.getMediaUrl());
        post.setVisibility(postDTO.getVisibility());
        post.setTitle(postDTO.getTitle());
        post.setTopic(postDTO.getTopic());
        post.setLevel(postDTO.getLevel());
        //post.setUser(postDTO.getUser());
        //post.setClaimer(userMapper.toEntity(postDTO.getClaimer()));
        post.setTimeCreated(postDTO.getTimeCreated() != null ? postDTO.getTimeCreated() : LocalDateTime.now()); // Handle default
        return post;
    }
}