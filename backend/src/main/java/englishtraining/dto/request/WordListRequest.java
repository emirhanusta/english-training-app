package englishtraining.dto.request;

import jakarta.annotation.Nonnull;

public record WordListRequest(
        @Nonnull
        String name
) {
}
