package enigma.estore.service.impl;

import enigma.estore.dto.request.user.UserDTO;
import enigma.estore.dto.request.user.UserDTO.RegisterRequest;
import enigma.estore.dto.request.user.UserDTO.LoginRequest;
import enigma.estore.model.User;
import enigma.estore.repository.UserRepository;
import enigma.estore.service.JwtService;
import enigma.estore.service.UserService;
import enigma.estore.utils.enums.Role;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    @Value("${admin.username}")
    private String adminUsername;
    @Value("${admin.password}")
    private String adminPassword;

    @Override
    public User register(RegisterRequest req) {
        if (userRepository.findByEmail(req.getEmail()).isPresent() || userRepository.findByUsername(req.getUsername()).isPresent()
        ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Credential invalid");
        }

        User user = User.builder()
                .email(req.getEmail())
                .username(req.getUsername())
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(Role.CUSTOMER)
                .build();

        return userRepository.save(user);
    }

    @Override
    public String login(LoginRequest req) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
        );

        User user = userRepository.findByUsername(req.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Credential"));
        return jwtService.generateToken(user);
    }

    @Override
    public Page<UserDTO.UserBasicFormat> index(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserDTO.UserBasicFormat::from);
    }

    @Override
    public User show(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

    @PostConstruct
    public void initAdmin() {
        Optional<User> cred = userRepository.findByUsername(adminUsername);
        if (cred.isPresent()) {
            return;
        }

        String hashedPassword = passwordEncoder.encode(adminPassword);

        User admin = User.builder()
                .email(adminUsername + "@email.com")
                .firstName("admin")
                .lastName("admin")
                .username(adminUsername)
                .password(hashedPassword)
                .role(Role.ADMIN)
                .build();

        userRepository.saveAndFlush(admin);
    }
}
