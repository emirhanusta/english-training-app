package englishtraining.service;

import englishtraining.dto.request.SignUpRequest;
import englishtraining.dto.response.UserDto;
import englishtraining.model.User;
import englishtraining.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AuthService authService;

    public UserService(UserRepository userRepository, AuthService authService) {
        this.userRepository = userRepository;
        this.authService = authService;
    }

    protected User saveUser(User user) {
        return userRepository.save(user);
    }

    protected boolean existsUserByName(String name) {
        return userRepository.existsByUsername(name);
    }


    protected User findUserByName(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username: " + username)
        );
    }

    protected User findUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id: " + id)
        );
    }

    public void deleteUser() {
        userRepository.deleteById(authService.getAuthenticatedUser().id());
    }

    public UserDto updateUser(SignUpRequest request) {
        User user = findUserById(authService.getAuthenticatedUser().id());
        user.setUsername(request.username());
        user.setPassword(request.password());
        user.setEmail(request.email());
        return UserDto.from(saveUser(user));
    }
}
