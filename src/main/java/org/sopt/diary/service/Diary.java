package org.sopt.diary.service;

import org.sopt.diary.enums.Category;

import java.time.LocalDateTime;

public class Diary {
    private final long id;
    private final String title;
    private final String body;
    private final LocalDateTime createdDate;
    private final Category category;

    public Diary(long id, String title, String body, LocalDateTime createdDate, Category category){
        this.id = id;
        this.title = title;
        this.body = body;
        this.createdDate = createdDate;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() { return body;}

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public Category getCategory() {
        return category;
    }
}
