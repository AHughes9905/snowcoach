package snowcoach.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import snowcoach.DTO.PostDTO;
import snowcoach.DTO.ReplyDTO;
import snowcoach.Service.MediaService;
import snowcoach.Service.PostService;
import snowcoach.Util.JwtUtil;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin
public class PostController {

    private final PostService postService;
    private final MediaService mediaService;
    private final JwtUtil jwtUtil;

    public PostController(PostService postService, MediaService mediaService, JwtUtil jwtUtil) {
        this.postService = postService;
        this.mediaService = mediaService;
        this.jwtUtil = jwtUtil;
    }

    @PreAuthorize("hasAuthority('WRITE')")
    @PostMapping("/create")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO dto, @CookieValue(value = "jwt", required = false) String jwt) {
        dto.setUsername(jwtUtil.extractUsername(jwt));
        PostDTO createdPost = postService.createPost(dto);
        return ResponseEntity.ok(createdPost);
    }

    @PreAuthorize("hasAuthority('WRITE')")
    @PostMapping("/create/media")
    public ResponseEntity<PostDTO> createPostWithMedia(
            @RequestPart("post") PostDTO dto,
            @RequestPart("file") MultipartFile file) {
        try {
            String mediaUrl = mediaService.uploadMedia(file);
            dto.setMediaUrl(mediaUrl);
            PostDTO createdPost = postService.createPost(dto);
            return ResponseEntity.ok(createdPost);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPost(@PathVariable Long id, @CookieValue(value = "jwt", required = false) String jwt) {
        // Checks in service if user is authorized to view post
        PostDTO post = postService.getPostById(id, jwtUtil.extractUsername(jwt));
        return ResponseEntity.ok(post);
    }

    @PreAuthorize("hasAuthority('WRITE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable Long id, @CookieValue(value = "jwt", required = false) String jwt) {
        try {
            // Service checks if user is authorized to delete post
            postService.deletePost(id, jwtUtil.extractUsername(jwt));
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('CLAIM')")
    @PutMapping("/{id}/claim")
    public ResponseEntity<Object> claimPost(@PathVariable Long id, @CookieValue(value = "jwt", required = false) String jwt) {
        try {
            PostDTO claimedPost = postService.claimPost(id, jwtUtil.extractUsername(jwt));
            return ResponseEntity.ok(claimedPost); // Return the claimed post if successful
        } catch (SecurityException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        } catch (RuntimeException e) {
            // If post is already claimed
            return ResponseEntity.status(400).body(null);
        }
    }

    // For when post is marked as complete by user or claimer
    @PreAuthorize("hasAuthority('WRITE')")
    @PutMapping("{id}/complete")
    public ResponseEntity<Object> completePost(@PathVariable Long id, @CookieValue(value = "jwt", required = false) String jwt) {
        try {
            PostDTO post = postService.completePost(id, jwtUtil.extractUsername(jwt));
            return ResponseEntity.ok(post);
        } catch (SecurityException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // Lists user's completed and uncompleted created posts
    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/my-posts")
    public ResponseEntity<List<PostDTO>> getUserPosts(@CookieValue(value = "jwt", required = false) String jwt) {
        List<PostDTO> posts = postService.getPostsByUsername(jwtUtil.extractUsername(jwt));
        return ResponseEntity.ok(posts);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDTO>> getPostsByUser(@PathVariable Long userId) {
        List<PostDTO> posts = postService.getPostsByUserId(userId);
        return ResponseEntity.ok(posts);
    }

    // Currently not utilized
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/visibility/{visibility}")
    public ResponseEntity<List<PostDTO>> getPostsByVisibility(@PathVariable String visibility) {
        List<PostDTO> posts = postService.getPostsByVisibility(visibility);
        return ResponseEntity.ok(posts);
    }

    // Lists all unclaimed posts
    @PreAuthorize("hasAuthority('CLAIM')")
    @GetMapping("/unclaimed")
    public ResponseEntity<List<PostDTO>> getUnclaimedPosts() {
        List<PostDTO> posts = postService.getUnclaimedPosts();
        return ResponseEntity.ok(posts);
    }

    //Gets posts claimed by requester
    @PreAuthorize("hasAuthority('CLAIM')")
    @GetMapping("/claimed")
    public ResponseEntity<List<PostDTO>> getClaimedPosts(@CookieValue(value = "jwt", required = false) String jwt) {
        List<PostDTO> posts = postService.getClaimedPosts(jwtUtil.extractUsername(jwt));
        return ResponseEntity.ok(posts);
    }

    // Reply to post
    @PreAuthorize("hasAuthority('WRITE')")
    @PostMapping("/{id}/reply")
    public ResponseEntity<Object> addReply(
            @RequestBody ReplyDTO reply,
            @CookieValue(value = "jwt", required = false) String jwt) {
        try {
            reply.setUsername(jwtUtil.extractUsername(jwt));
            PostDTO post = postService.addReply(reply);
            ReplyDTO replyDTO = post.getReplies().getLast();
            return ResponseEntity.ok(replyDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // Reply to post with media
    @PreAuthorize("hasAuthority('WRITE')")
    @PostMapping("/{id}/reply/media")
    public ResponseEntity<ReplyDTO> createPostWithMedia(
            @CookieValue(value = "jwt", required = false) String jwt,
            @RequestPart("reply") ReplyDTO reply,
            @RequestPart("file") MultipartFile file) {
        try {
            String mediaUrl = mediaService.uploadMedia(file);

            reply.setMediaUrl(mediaUrl);
            reply.setUsername(jwtUtil.extractUsername(jwt));

            PostDTO post = postService.addReply(reply);
            ReplyDTO replyDTO = post.getReplies().getLast();
            return ResponseEntity.ok(replyDTO);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}