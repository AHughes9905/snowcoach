package snowcoach.Service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class MediaService {

    private final Path mediaStorageLocation = Paths.get("media").toAbsolutePath().normalize();

    public MediaService() {
        try {
            Files.createDirectories(mediaStorageLocation);
        } catch (Exception e) {
            throw new RuntimeException("Could not create media storage directory", e);
        }
    }

    public String uploadMedia(org.springframework.web.multipart.MultipartFile file) throws Exception {
        // Extract the file extension
        String originalFileName = file.getOriginalFilename();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

        // Generate a unique filename using UUID
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

        Path targetLocation = mediaStorageLocation.resolve(uniqueFileName);

        Files.copy(file.getInputStream(), targetLocation);
        return "/media/" + uniqueFileName;
    }

    public Resource getMediaAsResource(String mediaId) throws Exception {
        Path filePath = mediaStorageLocation.resolve(mediaId).normalize();
        Resource resource = new UrlResource(filePath.toUri());
        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("Media not found: " + mediaId);
        }
    }
}