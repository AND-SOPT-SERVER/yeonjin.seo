package org.sopt.diary.api;

import java.time.LocalDateTime;

public class DiaryDetailResponse {
    private long id;
    private String title;
    private String body;
    private LocalDateTime createdDate;

    public DiaryDetailResponse(long id, String title, String body, LocalDateTime createdDate) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.createdDate = createdDate;
    }

    // Getters and setters
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
}
