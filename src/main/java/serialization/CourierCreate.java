package serialization;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CourierCreate {
    private String login;
    private String password;
    private String firstName;
    private Integer id;


    public CourierCreate(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }
}