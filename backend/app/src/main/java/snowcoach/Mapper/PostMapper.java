package snowcoach.Mapper;

import snowcoach.DTO.PostDTO;
import snowcoach.Model.Post;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PostMapper {

    private final UserMapper userMapper;
    private final ReplyMapper replyMapper;

    public PostMapper(UserMapper userMapper, ReplyMapper replyMapper) {
        this.userMapper = userMapper;
        this.replyMapper = replyMapper;
    }

    public PostDTO toDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setBody(post.getBody());
        postDTO.setMediaUrl(post.getMediaUrl());
        postDTO.setVisibility(post.getVisibility());
        postDTO.setUsername(post.getUser().getUsername());
        postDTO.setTitle(post.getTitle());
        postDTO.setTopic(post.getTopic());
        postDTO.setLevel(post.getLevel());
        postDTO.setSport(post.getSport());

        if (post.getClaimer() != null) {
            postDTO.setClaimer(post.getClaimer().getUsername());
            postDTO.setReplies(post.getReplies().stream().map(replyMapper::toDTO).toList());
        } else {
            postDTO.setClaimer(null);
        }

        postDTO.setTimeCreated(post.getTimeCreated());
        if (post.getClaimer() != null) {
            postDTO.setReplies(post.getReplies().stream().map(replyMapper::toDTO).toList());
        }

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
        post.setSport(postDTO.getSport());
        //post.setUser(postDTO.getUser());
        //post.setClaimer(userMapper.toEntity(postDTO.getClaimer()));
        post.setTimeCreated(postDTO.getTimeCreated() != null ? postDTO.getTimeCreated() : LocalDateTime.now()); // Handle default
        return post;
    }
}