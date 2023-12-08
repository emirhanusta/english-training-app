package englishtraining.service;

import englishtraining.dto.request.UserRequest;
import englishtraining.dto.response.UserDto;
import englishtraining.exception.UserNotFoundException;
import englishtraining.model.User;
import englishtraining.model.enums.Role;
import englishtraining.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;
    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    @DisplayName("should save and return User when given user")
    void shouldSaveAndReturnUser_WhenGivenUser() {
        //given
        User user = new User("username", "password", "email");
        //when
        when(userRepository.save(user)).thenReturn(user);
        //then
        User result = userService.saveUser(user);

        assertEquals(result, user);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    @DisplayName("should return true when given username and user exists")
    void   shouldReturnTrue_WhenGivenUsernameAndUserExists() {
        //given
        String username = "username";
        //when
        when(userRepository.existsByUsername(username)).thenReturn(true);
        //then
        boolean result = userService.existsUserByName(username);

        assertTrue(result);

        verify(userRepository, times(1)).existsByUsername(username);
    }

    @Test
    @DisplayName("should return false when given username and user does not exist")
    void shouldReturnFalse_WhenGivenUsernameAndUserDoesNotExist() {
        //given
        String username = "username";
        //when
        when(userRepository.existsByUsername(username)).thenReturn(false);
        //then
        boolean result = userService.existsUserByName(username);

        assertFalse(result);

        verify(userRepository, times(1)).existsByUsername(username);
    }

    @Test
    @DisplayName("should return User when given username and user exists")
    void shouldReturnUser_WhenGivenUsernameAndUserExists() {
        //given
        String username = "username";
        User user = new User("username", "password", "email");
        //when
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        //then
        User result = userService.findUserByName(username);

        assertEquals(result, user);

        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    @DisplayName("should throw UsernameNotFoundException when given username and user does not exist")
    void shouldThrowUserNotFoundException_WhenGivenUsernameAndUserDoesNotExist() {
        //given
        String username = "username";
        //when
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        //then
        assertThatThrownBy(() -> userService.findUserByName(username))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining("User not found with username: " + username);

        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    @DisplayName("should return User when given id and user exists")
    void shouldReturnUser_WhenGivenIdAndUserExists() {
        //given
        UUID id = UUID.randomUUID();
        User user = new User("username", "password", "email");
        //when
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        //then
        User result = userService.findUserById(id);

        assertEquals(result, user);

        verify(userRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("should delete User when given id and user exists")
    void shouldDeleteUser_WhenGivenIdAndUserExists() {
        //given
        UUID id = UUID.randomUUID();
        User user = new User("username", "password", "email");
        //when
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        //then
        userService.deleteUser(id);

        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    @DisplayName("should throw UserNotFoundException when given id and user does not exist")
    void shouldThrowUserNotFoundException_WhenGivenIdAndUserDoesNotExist() {
        //given
        UUID id = UUID.randomUUID();
        //when
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        //then
        assertThatThrownBy(() -> userService.deleteUser(id))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User not found with id: " + id);

        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(0)).deleteById(id);
    }

    @Test
    @DisplayName("should return UserDto when given id with UserRequest and user exists")
    void shouldReturnUserDto_WhenGivenIdWithUserRequestAndUserExists() {
        //given
        UUID id = UUID.randomUUID();
        UserRequest userRequest = new UserRequest("updated_name", "updated_password", "updated_email");
        User user = new User("username", "password", "email");
        user.setRole(Role.USER);
        user.setId(id);
        UserDto userDto = new UserDto(id,"username", "email", Objects.requireNonNull(user.getRole()).toString());
        //when
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        //then
        UserDto result = userService.updateUser(id, userRequest);

        assertEquals(result.id(), userDto.id());

        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).save(user);
    }


    @AfterEach
    void tearDown() {
    }
}