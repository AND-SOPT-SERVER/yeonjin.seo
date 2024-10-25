package org.sopt.diary.repository;

import jakarta.persistence.*;
import org.sopt.diary.enums.Category;

import java.time.LocalDateTime;

@Entity
public class DiaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false, unique = true)
    public String title;

    @Column(nullable = false)
    public String body;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Category category;

    public DiaryEntity() {

    }

    public DiaryEntity(String title, String body, Category category) {
        this.title = title;
        this.body = body;
        this.createdDate = LocalDateTime.now();
        this.category =  category;
    }

    public Long getId() {
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

    public Category getCategory() {
        return category;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
