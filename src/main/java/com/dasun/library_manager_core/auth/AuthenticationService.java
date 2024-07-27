package com.dasun.library_manager_core.auth;

import com.dasun.library_manager_core.config.JwtService;
import com.dasun.library_manager_core.user.Role;
import com.dasun.library_manager_core.user.User;
import com.dasun.library_manager_core.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstname())
                .LastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);
        var token = jwtService.generateToken(getClaims(user), user);
        return AuthenticationResponse.builder().token(token).build();
    }

    private  Map<String, Object> getClaims(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        return claims;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // if you get here means user is authenticated successfully
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var roles = user.getGroups().stream()
                .flatMap(group -> group.getRoles().stream())
                .map(Role::getName)
                .collect(Collectors.toList());

        var token = jwtService.generateToken(getClaims(user), user);
        return AuthenticationResponse.builder().token(token).roles(roles).build();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(long id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
