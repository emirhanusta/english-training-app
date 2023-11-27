package englishtraining.controller;

import englishtraining.dto.request.SignUpRequest;
import englishtraining.dto.response.UserDto;
import englishtraining.service.AuthService;
import englishtraining.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final AuthService authService;
    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getUserById() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(authService.getAuthenticatedUser());
    }

    @PutMapping("/update")
    public ResponseEntity<UserDto> updateUser(@RequestBody SignUpRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.updateUser(request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteUser() {
        userService.deleteUser();
        return ResponseEntity.noContent().build();
    }
}
