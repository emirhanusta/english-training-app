package englishtraining.dto;

import jakarta.annotation.Nonnull;

import java.util.List;
import java.util.UUID;

public record WordRequest(
        @Nonnull
        String name,
        @Nonnull
        String definition,
        String level,
        String status,
        List<String> exampleSentences,
        @Nonnull
        UUID wordListId
) {
}
