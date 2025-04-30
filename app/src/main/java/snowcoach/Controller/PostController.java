package snowcoach.Controller;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import snowcoach.DTO.PostDTO;
import snowcoach.Service.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO dto) {
        PostDTO createdPost = postService.createPost(dto);
        return ResponseEntity.ok(createdPost);
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
