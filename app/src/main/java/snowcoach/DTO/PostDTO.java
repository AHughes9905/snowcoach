package snowcoach.DTO;
import java.time.LocalDateTime;

public class PostDTO {

    private Long id;
    private String title;
    private String topic;
    private int level;
    private String body;
    private String mediaUrl;
    private String visibility;
    private UserDTO user;
    private UserDTO claimer;
    private LocalDateTime timeCreated; // New field

    public PostDTO() {}

    public PostDTO(Long id, String body, String title, String topic, int level, String mediaUrl, String visibility, UserDTO user, UserDTO owner, LocalDateTime timeCreated) {
        this.id = id;
        this.body = body;
        this.mediaUrl = mediaUrl;
        this.visibility = visibility;
        this.user = user;
        this.title = title;
        this.topic = topic;
        this.level = level;
        this.claimer = owner;
        this.timeCreated = timeCreated; // New field
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
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

    public String getTitle() { 
        return title; 
    }

    public void setTitle(String title) { 
        this.title = title; 
    }

    public String getTopic() { 
        return topic; 
    }

    public void setTopic(String topic) { 
        this.topic = topic; 
    }

    public int getLevel() { 
        return level; 
    }

    public void setLevel(int level) { 
        this.level = level; 
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(LocalDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }
}