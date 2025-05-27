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
        System.out.println("here");
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

    @PreAuthorize("hasAuthority('CLAIM')")
    @PutMapping("/{id}/claim")
    public ResponseEntity<Object> claimPost(@PathVariable Long id, @CookieValue(value = "jwt", required = false) String jwt) {
        try {
            // Call the service method
            PostDTO claimedPost = postService.claimPost(id, jwtUtil.extractUsername(jwt));
            return ResponseEntity.ok(claimedPost); // Return the claimed post if successful
        } catch (SecurityException e) {
            // Handle insufficient authority
            return ResponseEntity.status(403).body(e.getMessage());
        } catch (RuntimeException e) {
            // Handle other errors like post not found or already claimed
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("/my-posts")
    public ResponseEntity<List<PostDTO>> getUserPosts(@CookieValue(value = "jwt", required = false) String jwt) {
        List<PostDTO> posts = postService.getPostsByUsername(jwtUtil.extractUsername(jwt));
        return ResponseEntity.ok(posts);
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

    // Need to check if post is claimed and/or only poster and claimer can reply
    @PostMapping("/{id}/reply")
    public ResponseEntity<ReplyDTO> addReply(
            @RequestBody ReplyDTO reply,
            @CookieValue(value = "jwt", required = false) String jwt) {
        reply.setUsername(jwtUtil.extractUsername(jwt));
        PostDTO post = postService.addReply(reply);
        ReplyDTO replyDTO = post.getReplies().getLast();
        return ResponseEntity.ok(replyDTO);
    }

    @PostMapping("/{id}/reply/media")
    public ResponseEntity<ReplyDTO> createPostWithMedia(
            @CookieValue(value = "jwt", required = false) String jwt,
            @RequestPart("reply") ReplyDTO reply,
            @RequestPart("file") MultipartFile file) {
        try {
            // Upload the media file and get its URL
            String mediaUrl = mediaService.uploadMedia(file);

            // Attach the media URL to the post DTO
            reply.setMediaUrl(mediaUrl);
            reply.setUsername(jwtUtil.extractUsername(jwt));
            System.out.println("medial url for reply " + reply.getMediaUrl());
            // Create the post with the attached media
            PostDTO post = postService.addReply(reply);
            ReplyDTO replyDTO = post.getReplies().getLast();
            return ResponseEntity.ok(replyDTO);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}