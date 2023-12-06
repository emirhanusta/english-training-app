package englishtraining.dto.request;

public record UserRequest(
        String username,
        String password,
        String email
) {
}
