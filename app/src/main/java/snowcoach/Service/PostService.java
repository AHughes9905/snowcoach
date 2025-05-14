package snowcoach.Service;

import snowcoach.DTO.*;
import snowcoach.Mapper.PostMapper;
import snowcoach.Model.*;
import snowcoach.Repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public PostDTO createPost(PostDTO dto) {
        System.out.println(dto.getUsername());
        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = new Post();
        //need to move this to mapper
        post.setTimeCreated(LocalDateTime.now());
        post.setBody(dto.getBody());
        post.setVisibility("visible");
        post.setMediaUrl("na");
        post.setUser(user);
        post.setClaimer(null);
        post.setTitle(dto.getTitle());
        post.setLevel(dto.getLevel());
        post.setTopic(dto.getTopic());
        postRepository.save(post);
        return postMapper.toDTO(post); 
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

        post.setClaimer(user);
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

    public List<PostDTO> getUnclaimedPosts() {
        List<Post> posts = postRepository.findByClaimerId(null);
        return posts.stream().map(postMapper::toDTO).toList();
    }
}
