package snowcoach.DTO;

public class AuthRespDTO {
    private String message;
    private String userName;

    public AuthRespDTO(String userName, String message) {
        this.userName = userName;
        this.message = message;
    }

    public String setMessage() {
        return message;
    }

    public void setMessage(String token) {
        this.message = message;
    }

    public String getUserName() { return this.userName; }

    public void setUserName(String userName) { this.userName = userName; }

}
