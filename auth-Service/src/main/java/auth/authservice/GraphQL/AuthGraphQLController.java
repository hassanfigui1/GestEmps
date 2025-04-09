package auth.authservice.GraphQL;

import auth.authservice.DTO.AuthPayload;
import auth.authservice.DTO.UserProfile;
import auth.authservice.Entity.User;
import auth.authservice.Repository.UserRepository;
import auth.authservice.Service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class AuthGraphQLController {
    private final AuthenticationService authService;
    private final UserRepository userRepository;

    @MutationMapping
    public AuthPayload login(@Argument String username, @Argument String password) {
        System.out.println("Username: " + username + ", Password: " + password);
        return authService.login(username, password);
    }

    @MutationMapping
    public User register(
            @Argument String username,
            @Argument String password,
            @Argument String email,
            @Argument Set<User.UserRole> roles
    ) {
        return authService.register(username, password, email, roles);
    }

    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})     @QueryMapping
    public List<UserProfile> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserProfile::new) // Convert to UserProfile DTO
                .collect(Collectors.toList());
    }

    // Query to get user by username
    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"}) // Ensures only ADMIN and MANAGER can access
    @QueryMapping
    public UserProfile getUserByUsername(@Argument String username) {
        return userRepository.findByUsername(username)
                .map(UserProfile::new)
                .orElse(null); // Return null if user not found
    }

    // Query to get current authenticated user
    @QueryMapping
    public UserProfile getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        return userRepository.findByUsername(userDetails.getUsername())
                .map(UserProfile::new)
                .orElse(null); // Return null if current user not found
    }

}