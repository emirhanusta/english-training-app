package englishtraining.service;

import englishtraining.dto.request.UserRequest;
import englishtraining.dto.response.UserDto;
import englishtraining.exception.UserNotFoundException;
import englishtraining.model.User;
import englishtraining.repository.UserRepository;
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

    public void deleteUser(UUID id) {
        userRepository.delete(findUserById(id));
    }

    public UserDto updateUser(UUID id, UserRequest request) {
        User user = findUserById(id);
        user.setUsername(request.username());
        user.setPassword(request.password());
        user.setEmail(request.email());
        return UserDto.from(saveUser(user));
    }

}
