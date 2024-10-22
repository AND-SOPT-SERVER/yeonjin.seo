package org.sopt.diary.repository;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class DiaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public String title;

    @Column(nullable = false)
    public String body;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    public DiaryEntity() {

    }

    public DiaryEntity(String title, String body) {
        this.title = title;
        this.body = body;
        this.createdDate = LocalDateTime.now();
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

    public void setBody(String body) {
        this.body = body;
    }

}
