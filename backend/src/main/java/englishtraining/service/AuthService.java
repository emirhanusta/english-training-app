package englishtraining.service;

import englishtraining.dto.request.LoginRequest;
import englishtraining.dto.request.UserRequest;
import englishtraining.dto.response.TokenDto;
import englishtraining.dto.response.UserDto;
import englishtraining.exception.AlreadyExistException;
import englishtraining.model.User;
import englishtraining.model.enums.Role;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;
    private final UserService userService;
    private final TokenService tokenService;

    public AuthService(AuthenticationManager authenticationManager, PasswordEncoder encoder, UserService userService, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.encoder = encoder;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    public TokenDto login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.username(),
                            loginRequest.password()
                    )
            );
            return new TokenDto(
                    tokenService.generateToken(authentication),
                    UserDto.from(userService.findUserByName(loginRequest.username()))
            );
    } catch (final BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password");
        }
    }

    public UserDto signup(UserRequest userRequest) {
        boolean existsUserByName = userService.existsUserByName(userRequest.username());
        if (existsUserByName) {
            throw new AlreadyExistException("Username is already taken!");
        }
        User user = new User(
                userRequest.username(),
                encoder.encode(userRequest.password()),
                userRequest.email(),
                Role.USER
        );
        return UserDto.from(userService.saveUser(user));
    }

    public UserDto getAuthenticatedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return UserDto.from(userService.findUserByName(username));
    }
}
