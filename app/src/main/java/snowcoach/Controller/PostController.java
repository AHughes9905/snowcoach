package snowcoach.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import snowcoach.DTO.PostDTO;
import snowcoach.DTO.ReplyDTO;
import snowcoach.Model.Reply;
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

    @PostMapping("/create")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO dto, @CookieValue(value = "jwt", required = false) String jwt) {
        dto.setUsername(jwtUtil.extractUsername(jwt));
        PostDTO createdPost = postService.createPost(dto);
        return ResponseEntity.ok(createdPost);
    }

    @PostMapping("/create/media")
    public ResponseEntity<PostDTO> createPostWithMedia(
            @RequestPart("post") PostDTO dto,
            @RequestPart("file") MultipartFile file) {
        try {
            // Upload the media file and get its URL
            String mediaUrl = mediaService.uploadMedia(file);

            // Attach the media URL to the post DTO
            dto.setMediaUrl(mediaUrl);

            // Create the post with the attached media
            PostDTO createdPost = postService.createPost(dto);
            return ResponseEntity.ok(createdPost);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPost(@PathVariable Long id) {
        PostDTO post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/claim")
    //@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<PostDTO> claimPost(@PathVariable Long id, @CookieValue(value = "jwt", required = false) String jwt) {
        PostDTO claimedPost = postService.claimPost(id, jwtUtil.extractUsername(jwt));
        return ResponseEntity.ok(claimedPost);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDTO>> getPostsByUser(@PathVariable Long userId) {
        List<PostDTO> posts = postService.getPostsByUserId(userId);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/visibility/{visibility}")
    public ResponseEntity<List<PostDTO>> getPostsByVisibility(@PathVariable String visibility) {
        List<PostDTO> posts = postService.getPostsByVisibility(visibility);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/unclaimed")
    public ResponseEntity<List<PostDTO>> getUnclaimedPosts() {
        List<PostDTO> posts = postService.getUnclaimedPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/claimed")
    public ResponseEntity<List<PostDTO>> getClaimedPosts(@CookieValue(value = "jwt", required = false) String jwt) {
        List<PostDTO> posts = postService.getCalimedPosts(jwtUtil.extractUsername(jwt));
        return ResponseEntity.ok(posts);
    }

    @PostMapping("/{id}/reply")
    public ResponseEntity<PostDTO> addReply(@RequestBody ReplyDTO reply, @CookieValue(value = "jwt", required = false) String jwt) {
        reply.setUsername(jwtUtil.extractUsername(jwt));
        PostDTO post = postService.addReply(reply);
        return ResponseEntity.ok(post);
    }
}
