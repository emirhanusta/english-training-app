package englishtraining.dto.response;

import englishtraining.model.User;

import java.util.Objects;
import java.util.UUID;

public record UserDto(
        UUID id,
        String username,
        String email,
        String role
) {
        public static UserDto from(User user) {
                return new UserDto(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        Objects.requireNonNull(user.getRole()).name()
                );
        }
}
