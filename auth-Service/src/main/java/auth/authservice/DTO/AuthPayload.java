package auth.authservice.DTO;

import auth.authservice.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthPayload {
    private String token;
    private User user;
}