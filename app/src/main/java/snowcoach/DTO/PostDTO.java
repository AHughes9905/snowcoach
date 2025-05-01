package snowcoach.DTO;

public class PostDTO {

    private Long id;
    private String content;
    private String mediaUrl;
    private String visibility;
    private UserDTO user;
    private UserDTO claimer; // Assuming User is another DTO or entity class

    public PostDTO() {}

    public PostDTO(Long id, String content, String mediaUrl, String visibility, UserDTO user, UserDTO owner) {
        this.id = id;
        this.content = content;
        this.mediaUrl = mediaUrl;
        this.visibility = visibility;
        this.user = user;
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

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public UserDTO getClaimer() {
        return claimer;
    }

    public void setClaimer(UserDTO claimer) {
        this.claimer = claimer;
    }

}
