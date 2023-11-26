package englishtraining.dto.response;

public record TokenDto(
        String token,
        UserDto user
) {
}
