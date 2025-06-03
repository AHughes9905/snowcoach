package snowcoach.Service;

import snowcoach.DTO.*;
import snowcoach.Mapper.*;
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
    private final ReplyMapper replyMapper;
    private final UserService userService;
    private final PrivilegeRepository privilegeRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository, PostMapper postMapper, ReplyMapper replyMapper, UserService userService, PrivilegeRepository privilegeRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postMapper = postMapper;
        this.replyMapper = replyMapper;
        this.userService = userService;
        this.privilegeRepository = privilegeRepository;
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
        post.setMediaUrl(dto.getMediaUrl() != null ? dto.getMediaUrl() : "");
        post.setUser(user);
        post.setClaimer(null);
        post.setTitle(dto.getTitle());
        post.setLevel(dto.getLevel());
        post.setTopic(dto.getTopic());
        postRepository.save(post);
        return postMapper.toDTO(post); 
    }

    public PostDTO getPostById(Long id, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        System.out.println("user id" + user.getId());
        System.out.println("post usrer" + post.getUser().getId());
        System.out.println("post claimer" + post.getClaimer().getId());
        if (post.getClaimer() != null
                && !(post.getClaimer().getId().equals(user.getId()) || post.getUser().getId().equals(user.getId()))) {
            throw new SecurityException("User is not authorized to view this post");
        }
        return postMapper.toDTO(post);
    }

    public boolean deletePost(Long id, String username) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        boolean hasRequiredPrivilege = new UserPrincipal(user).getAuthorities()
                .stream()
                .anyMatch(authority -> authority.getAuthority().equals("ADMIN"));
        if (!hasRequiredPrivilege) {
            throw new SecurityException("User is not authorized to delete this post");
        }
        postRepository.delete(post);
        return true;
    }

    public PostDTO claimPost(Long postId, String username) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (post.getClaimer() != null) {
            throw new RuntimeException("Post already claimed");
        }

        // Check for required privileges based on post level
        String requiredPrivilege = "TEACH" + post.getLevel();
        boolean hasRequiredPrivilege = new UserPrincipal(user).getAuthorities()
                .stream()
                .anyMatch(authority -> authority.getAuthority().equals(requiredPrivilege));

        if (!hasRequiredPrivilege) {
            // Indicate insufficient authority to claim this post level
            throw new SecurityException("Insufficient authority to claim level " + post.getLevel() + " post");
        }

        post.setClaimer(user);
        System.out.println("Claimer: " + post.getClaimer().getUsername());
        return postMapper.toDTO(postRepository.save(post));
    }

    public List<PostDTO> getPostsByUsername(String username) {
        User user = userService.getUserByName(username);
        return getPostsByUserId(user.getId());
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

    //Only gets claimed posts that are not completed
    public List<PostDTO> getClaimedPosts(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Post> posts = postRepository.findByClaimer_UsernameAndVisibilityNot(user.getUsername(), "completed");
        return posts.stream().map(postMapper::toDTO).toList();
    }

    public PostDTO addReply(ReplyDTO replyDTO) {
        Post post = replyDTO.getPostId() != null ? postRepository.findById(replyDTO.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found")) : null;
        User user = userRepository.findByUsername(replyDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (user != post.getClaimer() || user != post.getUser()) {
            throw new SecurityException("User is not authorized to add reply");
        }
        if (post.getVisibility().equals("completed")) {
            throw new RuntimeException("Post is already completed");
        }
        Reply reply = ReplyMapper.toEntity(replyDTO, post);
        post.addReply(reply);

        return postMapper.toDTO(postRepository.save(post));
    }

    public PostDTO completePost(Long postId, String username) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (post.getClaimer() == null) {
            throw new RuntimeException("Post is not claimed");
        }
        if (!post.getClaimer().getId().equals(user.getId()) || !post.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("User authorized to complete post is not the claimer or the creator of the post");
        }
        post.setVisibility("completed");
        return postMapper.toDTO(postRepository.save(post));
    }
}