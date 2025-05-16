package snowcoach.DTO;

public class ReplyDTO {
    private Long id;
    private String content;
    private String username;
    private Long postId;

    public ReplyDTO() {}

    public ReplyDTO(Long id, String content, String username, Long postId) {
        this.id = id;
        this.content = content;
        this.username = username;
        this.postId = postId;
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
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public Long getPostId() {
        return postId;
    }
    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
