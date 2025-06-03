package snowcoach.DTO;
import java.time.LocalDateTime;
import java.util.List;

public class PostDTO {

    private Long id;
    private String title;
    private String topic;
    private int level;
    private String body;
    private String mediaUrl;
    private String visibility;
    private String username;
    private String claimerName;
    private LocalDateTime timeCreated; // New field
    private List<ReplyDTO> replies;

    public PostDTO() {}

    public PostDTO(Long id, String body, String title, String topic, int level, String mediaUrl, String visibility, String username, String claimerName, LocalDateTime timeCreated, List<ReplyDTO> replies) {
        this.id = id;
        this.body = body;
        this.mediaUrl = mediaUrl;
        this.visibility = visibility;
        this.username = username;
        this.title = title;
        this.topic = topic;
        this.level = level;
        this.claimerName = claimerName;
        this.timeCreated = timeCreated;
        this.replies = replies;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClaimer() {
        return claimerName;
    }

    public void setClaimer(String claimerName) {
        this.claimerName = claimerName;
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

    public List<ReplyDTO> getReplies() {
        return replies;
    }

    public void setReplies(List<ReplyDTO> replies) {
        this.replies = replies;
    }
}