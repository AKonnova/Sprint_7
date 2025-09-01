package serialization;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CourierLogin {
    private String login;
    private String password;


    public CourierLogin(String login, String password) {
        this.login = login;
        this.password = password;
    }
}