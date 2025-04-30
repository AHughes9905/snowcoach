package Mapper;

import DTO.PostDTO;
import Model.Post;
import Model.User;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public PostDTO toDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setContent(post.getContent());
        postDTO.setMediaUrl(post.getMediaUrl());
        postDTO.setVisibility(post.getVisibility());
        postDTO.setUserId(post.getUser().getId());  // Assuming User has a getId() method
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