package englishtraining.service;

import englishtraining.dto.request.LoginRequest;
import englishtraining.dto.request.UserRequest;
import englishtraining.dto.response.TokenDto;
import englishtraining.dto.response.UserDto;
import englishtraining.exception.AlreadyExistException;
import englishtraining.model.User;
import englishtraining.model.enums.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import org.springframework.security.authentication.BadCredentialsException;

class AuthServiceTest {

    private  AuthenticationManager authenticationManager;
    private  PasswordEncoder passwordEncoder;
    private  UserService userService;
    private  TokenService tokenService;
    private AuthService authService;
    private Authentication authentication;
    private SecurityContext securityContext;

    @BeforeEach
    void setUp() {
        authenticationManager = mock(AuthenticationManager.class);
        passwordEncoder = mock(PasswordEncoder.class);
        userService = mock(UserService.class);
        tokenService = mock(TokenService.class);
        authentication = mock(Authentication.class);
        securityContext = mock(SecurityContext.class);
        authService = new AuthService(authenticationManager, passwordEncoder, userService, tokenService);
    }

    @Test
    @DisplayName("should return TokenDto when given LoginRequest")
    void shouldReturnTokenDto_WhenGivenLoginRequest() {
        //given
        String username = "testUser";
        String password = "testPassword";
        LoginRequest loginRequest = new LoginRequest(username, password);
        String token = "generatedToken";
        User user = new User(username, password, "test@example.com");

        // when
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(tokenService.generateToken(authentication)).thenReturn(token);
        when(userService.findUserByName(username)).thenReturn(user);

        //then
        TokenDto result = authService.login(loginRequest);

        assertEquals(token, result.token());
        assertEquals(username, result.user().username());

        // Verify
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenService, times(1)).generateToken(authentication);
    }

    @Test
    @DisplayName("should throw BadCredentialsException when credentials are incorrect")
    void shouldThrowBadCredentialsException_WhenCredentialsAreIncorrect() {
        // given
        String username = "testUser";
        String password = "incorrectPassword";
        LoginRequest loginRequest = new LoginRequest(username, password);

        // when
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Incorrect username or password"));

        // then
        assertThrows(BadCredentialsException.class, () -> authService.login(loginRequest));

        // Verify
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    @DisplayName("should return UserDto when given UserRequest and user is not already taken")
    void shouldReturnUserDto_WhenGivenUserRequestAndUserIsNotAlreadyTaken() {
        // given
        String username = "newUser";
        String password = "newPassword";
        String email = "newuser@example.com";
        UserRequest userRequest = new UserRequest(username, password, email);
        String encodedPassword = "encodedPassword";
        User savedUser = new User(username, encodedPassword, email, Role.USER);

        // when
        when(userService.existsUserByName(username)).thenReturn(false);
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(userService.saveUser(any(User.class))).thenReturn(savedUser);

        //then
        UserDto result = authService.signup(userRequest);

        assertEquals(username, result.username());
        assertEquals(email, result.email());

        // Verify
        verify(userService, times(1)).existsUserByName(username);
        verify(passwordEncoder, times(1)).encode(password);
        verify(userService, times(1)).saveUser(any(User.class));
    }

    @Test
    @DisplayName("should throw AlreadyExistException when user already exists")
    void shouldThrowAlreadyExistException_WhenUserExists() {
        // given
        String existingUsername = "existingUser";
        UserRequest userRequest = new UserRequest(existingUsername, "password", "existinguser@example.com");

        // when
        when(userService.existsUserByName(existingUsername)).thenReturn(true);

        // then
        assertThrows(AlreadyExistException.class, () -> authService.signup(userRequest));

        // Verify
        verify(userService, times(1)).existsUserByName(existingUsername);
    }

    @Test
    void shouldReturnAuthenticatedUserDto() {
        // given
        String username = "testUser";
        User user = new User(username, "password", "test@example.com");

        SecurityContextHolder.setContext(securityContext);

        // when
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(username);
        when(userService.findUserByName(username)).thenReturn(user);

        // then
        UserDto result = authService.getAuthenticatedUser();

        assertEquals(username, result.username());

        // Verify
        verify(userService, times(1)).findUserByName(username);
    }
    @AfterEach
    void tearDown() {
    }
}