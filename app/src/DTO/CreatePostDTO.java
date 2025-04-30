package DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreatePostDTO {

    @NotNull
    @Size(min = 1, max = 500)
    private String content;

    @NotNull
    private String visibility; // e.g., "PUBLIC", "PRIVATE", "RESTRICTED"

    private String mediaUrl;   // points to an uploaded image/video

    @NotNull
    private Long userId;       // ID of the user creating the post

    public CreatePostDTO() {}

    public CreatePostDTO(String content, String visibility, String mediaUrl, Long userId) {
        this.content = content;
        this.visibility = visibility;
        this.mediaUrl = mediaUrl;
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}