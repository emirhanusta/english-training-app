package englishtraining.dto.response;

import englishtraining.model.User;

public record UserDto(
        String username,
        String email
) {
        public static UserDto from(User user) {
                return new UserDto(
                        user.getUsername(),
                        user.getEmail()
                );
        }
}
