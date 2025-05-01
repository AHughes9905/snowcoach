package snowcoach.Mapper;

import snowcoach.DTO.PostDTO;
import snowcoach.DTO.UserDTO;
import snowcoach.Model.Post;
import snowcoach.Model.User;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    private final UserMapper userMapper;

    public PostMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public PostDTO toDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setContent(post.getContent());
        postDTO.setMediaUrl(post.getMediaUrl());
        postDTO.setVisibility(post.getVisibility());
        postDTO.setUser(userMapper.toDTO(post.getUser()));  // Map User to UserDTO
        return postDTO;
    }

    public Post toEntity(PostDTO postDTO, User user) {
        Post post = new Post();
        post.setId(postDTO.getId());
        post.setContent(postDTO.getContent());
        post.setMediaUrl(postDTO.getMediaUrl());
        post.setVisibility(postDTO.getVisibility());
        post.setUser(user);  // Connect to user
        return post;
    }
}