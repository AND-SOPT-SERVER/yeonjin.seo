package org.sopt.seminar3.diary.repository;

import jakarta.persistence.*;
import org.sopt.seminar3.diary.enums.Category;
import org.sopt.seminar3.member.repository.MemberEntity;

import java.time.LocalDateTime;

@Entity
@Table(name="diary", uniqueConstraints = @UniqueConstraint(columnNames = {"member_id", "title"}))
public class DiaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, referencedColumnName = "id")
    private MemberEntity member;

    @Column(nullable = false, length = 100)
    public String title;

    @Column(nullable = false, length = 100)
    public String body;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    public DiaryEntity() {
    }

    public DiaryEntity(MemberEntity member, String title, String body, Category category) {
        this.member = member;
        this.title = title;
        this.body = body;
        this.category = category;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public MemberEntity getMember() {
        return member;
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

    public Category getCategory() {
        return category;
    }

    public void setBody(String body) {
        this.body = body;
    }

}