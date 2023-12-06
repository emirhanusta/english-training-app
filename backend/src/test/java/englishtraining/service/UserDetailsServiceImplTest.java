package englishtraining.service;

import englishtraining.model.User;
import englishtraining.model.enums.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDetailsServiceImplTest {

    private UserDetailsServiceImpl userDetailsService;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        userDetailsService = new UserDetailsServiceImpl(userService);
    }

    @Test
    @DisplayName("should return UserDetails when given username and user exists")
    void shouldReturnUserDetails_WhenGivenUsernameAndUserExists() {
        //given
        String username = "testUser";
        User user = new User(username, "password", "test@example.com");
        user.setRole(Role.USER);

        // Mock userService.findUserByName
        when(userService.findUserByName(username)).thenReturn(user);

        //when
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        //then
        assertEquals(username, userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
        assertEquals(Collections.singletonList("USER").toString(), userDetails.getAuthorities().toString());

        // Verify
        verify(userService, times(1)).findUserByName(username);
    }

    @AfterEach
    void tearDown() {
    }
}