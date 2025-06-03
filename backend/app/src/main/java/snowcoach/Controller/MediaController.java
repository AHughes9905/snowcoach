package snowcoach.Controller;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import snowcoach.Service.MediaService;

@RestController
@RequestMapping("/api/media")
@CrossOrigin
public class MediaController {

    private final MediaService mediaService;

    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @PreAuthorize("hasAuthority('WRITE')")
    @PostMapping("/upload")
    public ResponseEntity<String> uploadMedia(@RequestParam("file") org.springframework.web.multipart.MultipartFile file) {
        try {
            String mediaUrl = mediaService.uploadMedia(file);
            return ResponseEntity.status(HttpStatus.CREATED).body(mediaUrl);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload media");
        }
    }

    //Obsolete, currently user media server
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/{mediaId}")
    public ResponseEntity<Resource> getMedia(@PathVariable String mediaId) {
        try {
            Resource mediaResource = mediaService.getMediaAsResource(mediaId);

            // Set content disposition to indicate a downloadable file
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + mediaResource.getFilename() + "\"");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(mediaResource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}