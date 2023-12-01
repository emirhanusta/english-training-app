package englishtraining.dto.request;

import jakarta.annotation.Nonnull;

import java.util.UUID;

public record WordListRequest(
        @Nonnull
        String name,
        @Nonnull
        UUID userId
) {
}
