package snowcoach.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import snowcoach.DTO.PostDTO;
import snowcoach.Service.MediaService;
import snowcoach.Service.PostService;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin
public class PostController {

    private final PostService postService;
    private final MediaService mediaService;

    public PostController(PostService postService, MediaService mediaService) {
        this.postService = postService;
        this.mediaService = mediaService;
    }

    @PostMapping("/create")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO dto) {
        System.out.println("Request Body Username: " + dto.getUsername());
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
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<PostDTO> claimPost(@PathVariable Long id, @RequestParam Long userId) {
        PostDTO claimedPost = postService.claimPost(id, userId);
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
}
