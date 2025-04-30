package DTO;

public class PostDTO {

    private Long id;
    private String content;
    private String mediaUrl;
    private String visibility;
    private Long userId;
    private User owner; // Assuming User is another DTO or entity class

    public PostDTO() {}

    public PostDTO(Long id, String content, String mediaUrl, String visibility, Long userId, User owner) {
        this.id = id;
        this.content = content;
        this.mediaUrl = mediaUrl;
        this.visibility = visibility;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

}
