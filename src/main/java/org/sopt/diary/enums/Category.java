package org.sopt.diary.enums;

public enum Category {
    FOOD("음식"),
    EXERCISE("운동"),
    TRAVEL("여행"),
    HOBBY("취미"),
    PERSONAL("개인"),
    WORK("업무"),
    STUDY("학습");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Category fromKorean(String korean) {
        if (korean == null || korean.isEmpty()) {
            return null;
        }
        for (Category category : Category.values()) {
            if (category.getDisplayName().equals(korean)) {
                return category;
            }
        }
        throw new IllegalArgumentException("유효하지 않은 카테고리입니다: " + korean);
    }

}
