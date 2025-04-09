package auth.authservice.Service;

import auth.authservice.Config.JwtTokenProvider;
import auth.authservice.DTO.AuthPayload;
import auth.authservice.Entity.User;
import auth.authservice.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    @Transactional
    public AuthPayload login(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        // Generate token
        String token = tokenProvider.generateToken(authentication);
        // Update last login
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
        return new AuthPayload(token, user);
    }

    @Transactional
    public User register(String username, String password, String email, Set<User.UserRole> roles) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setRoles(roles != null ? roles : Set.of(User.UserRole.EMPLOYEE));
        user.setCreatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    @Transactional
    public boolean changePassword(String username, String currentPassword, String newPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Verify current password
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }

        // Encode and set new password
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return true;
    }
}