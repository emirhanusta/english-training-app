package englishtraining.dto.request;

import jakarta.annotation.Nonnull;

import java.util.UUID;

public record DiaryRequest(
        @Nonnull
        String title,
        String content,
        @Nonnull
        UUID userId
) {
}
