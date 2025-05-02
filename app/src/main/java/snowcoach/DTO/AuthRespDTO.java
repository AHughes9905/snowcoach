package snowcoach.DTO;

public class AuthRespDTO {
    private String token;

    public AuthRespDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
