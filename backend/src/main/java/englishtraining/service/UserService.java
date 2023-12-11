package englishtraining.service;

import englishtraining.dto.request.UserRequest;
import englishtraining.dto.response.UserDto;
import englishtraining.exception.UserNotFoundException;
import englishtraining.model.User;
import englishtraining.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final WordListService wordListService;


    public UserService(UserRepository userRepository, @Lazy WordListService wordListService) {
        this.userRepository = userRepository;
        this.wordListService = wordListService;
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

    @Transactional
    public void deleteUser(UUID id) {
        User user = findUserById(id);
        wordListService.deleteAllByUserId(id);
        userRepository.delete(user);
    }

    public UserDto updateUser(UUID id, UserRequest request) {
        User user = findUserById(id);
        user.setUsername(request.username());
        user.setPassword(request.password());
        user.setEmail(request.email());
        return UserDto.from(saveUser(user));
    }

}
