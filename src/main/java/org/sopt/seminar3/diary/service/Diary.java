package org.sopt.seminar3.diary.service;

import org.sopt.seminar3.diary.enums.Category;

import java.time.LocalDateTime;

public class Diary {
    private final long id;
    private final String title;
    private final String body;
    private final LocalDateTime createdAt;
    private final Category category;

    public Diary(long id, String title, String body, LocalDateTime createdAt, Category category){
        this.id = id;
        this.title = title;
        this.body = body;
        this.createdAt = createdAt;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() { return body;}

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Category getCategory() {
        return category;
    }
}