package englishtraining.dto;

import jakarta.annotation.Nonnull;

public record DiaryRequest(
        @Nonnull
        String title,
        String content
) {
}
