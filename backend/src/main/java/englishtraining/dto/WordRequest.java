package englishtraining.dto;

import jakarta.annotation.Nonnull;

import java.util.List;

public record WordRequest(
        @Nonnull
        String name,
        @Nonnull
        String definition,
        String level,
        String status,
        List<String> exampleSentences
) {
}
