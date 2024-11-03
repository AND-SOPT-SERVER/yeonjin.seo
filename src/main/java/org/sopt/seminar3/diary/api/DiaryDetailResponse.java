package org.sopt.seminar3.diary.api;

import org.sopt.seminar3.diary.enums.Category;

import java.time.LocalDateTime;


public class DiaryDetailResponse {
    private long id;
    private String title;
    private String body;
    private LocalDateTime createdAt;
    private String category;

    public DiaryDetailResponse(long id, String title, String body, LocalDateTime createdAt, Category category) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.createdAt = createdAt;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getCategory() {
        return category;
    }
}