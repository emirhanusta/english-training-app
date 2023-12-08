package englishtraining.controller;


import englishtraining.dto.request.LoginRequest;
import englishtraining.dto.request.UserRequest;
import englishtraining.dto.response.TokenDto;
import englishtraining.dto.response.UserDto;
import englishtraining.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> authenticate(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserRequest userRequest){
        return ResponseEntity.ok(authService.signup(userRequest));
    }

}

