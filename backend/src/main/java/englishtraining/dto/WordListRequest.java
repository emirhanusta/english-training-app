package englishtraining.dto;

import jakarta.annotation.Nonnull;

public record WordListRequest(
        @Nonnull
        String name
) {
}
