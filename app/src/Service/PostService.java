package Service;

import DTO.*;
import Mapper.PostMapper;
import Model.*;
import Repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;

    public PostService(PostRepository postRepository, UserRepository userRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postMapper = postMapper;
    }

    public Post createPost(CreatePostDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = new Post();
        post.setContent(dto.getContent());
        post.setVisibility(dto.getVisibility());
        post.setMediaUrl(dto.getMediaUrl());
        post.setUser(user);

        return postRepository.save(post);
    }

    public PostDTO getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return postMapper.toDTO(post);
    }

    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        postRepository.delete(post);
    }

    public PostDTO claimPost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        post.setUser(user);
        return postMapper.toDTO(postRepository.save(post));
    }

    public List<PostDTO> getPostsByUserId(Long userId) {
        List<Post> posts = postRepository.findByUserId(userId);
        return posts.stream().map(postMapper::toDTO).toList();
    }

    public List<PostDTO> getPostsByVisibility(String visibility) {
        List<Post> posts = postRepository.findByVisibility(visibility);
        return posts.stream().map(postMapper::toDTO).toList();
    }
}
