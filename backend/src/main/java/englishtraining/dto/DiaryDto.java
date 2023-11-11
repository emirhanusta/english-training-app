package englishtraining.dto;

import englishtraining.model.Diary;

import java.util.UUID;

public record DiaryDto(
        UUID id,
        String title,
        String content
) {
    public static DiaryDto from(Diary diary) {
        return new DiaryDto(
                diary.getId(),
                diary.getTitle(),
                diary.getContent()
        );
    }
}
