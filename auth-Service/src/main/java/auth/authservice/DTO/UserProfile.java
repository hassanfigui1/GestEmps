package auth.authservice.DTO;

import auth.authservice.Entity.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UserProfile {
    private String id;
    private String username;
    private String email;
    private Set<User.UserRole> roles;
    private LocalDateTime createdAt;

    public UserProfile(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.roles = user.getRoles();
        this.createdAt = user.getCreatedAt();
    }
}

