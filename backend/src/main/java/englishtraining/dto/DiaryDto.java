package englishtraining.dto;

import englishtraining.model.Diary;

public record DiaryDto(
        String id,
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
