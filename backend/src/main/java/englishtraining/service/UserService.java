package englishtraining.service;

import englishtraining.dto.request.SignUpRequest;
import englishtraining.dto.response.UserDto;
import englishtraining.exception.UserNotFoundException;
import englishtraining.model.User;
import englishtraining.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
                () -> new UserNotFoundException("User not found with id: " + id)
        );
    }

    public void deleteUser() {
        userRepository.deleteById(getAuthenticatedUser().id());
    }

    public UserDto updateUser(SignUpRequest request) {
        User user = findUserById(getAuthenticatedUser().id());
        user.setUsername(request.username());
        user.setPassword(request.password());
        user.setEmail(request.email());
        return UserDto.from(saveUser(user));
    }

    public UserDto getAuthenticatedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return UserDto.from(findUserByName(username));
    }
}
