package org.sopt.seminar3.diary.enums;

public enum Category {
    FOOD("음식"),
    EXERCISE("운동"),
    SCHOOL("학교"),
    MOVIE("영화");

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