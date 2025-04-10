    package auth.authservice.Service;
    import auth.authservice.Entity.User;
    import auth.authservice.Repository.UserRepository;
    import lombok.RequiredArgsConstructor;
    import org.springframework.security.core.authority.SimpleGrantedAuthority;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.core.userdetails.UsernameNotFoundException;
    import org.springframework.stereotype.Service;

    import java.util.stream.Collectors;

    @Service
    @RequiredArgsConstructor
    public class UserService implements UserDetailsService {
        private final UserRepository userRepository;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getUsername())
                    .password(user.getPassword())
                    .authorities(user.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority(role.name()))
                            .collect(Collectors.toList())
                    )
                    .build();
        }
    }