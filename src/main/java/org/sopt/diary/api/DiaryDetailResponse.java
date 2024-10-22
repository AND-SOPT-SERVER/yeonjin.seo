package org.sopt.diary.api;

import org.sopt.diary.enums.Category;

import java.time.LocalDateTime;


public class DiaryDetailResponse {
    private long id;
    private String title;
    private String body;
    private LocalDateTime createdDate;
    private String category;

    public DiaryDetailResponse(long id, String title, String body, LocalDateTime createdDate, Category category) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.createdDate = createdDate;
        this.category = (category != null) ? category.getDisplayName() : null;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public String getCategory() {
        return category;
    }
}
