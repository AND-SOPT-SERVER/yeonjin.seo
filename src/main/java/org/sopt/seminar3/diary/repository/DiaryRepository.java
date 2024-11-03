package org.sopt.seminar3.diary.repository;

import org.sopt.seminar3.diary.enums.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface DiaryRepository extends JpaRepository<DiaryEntity, Long> {
    @Query("SELECT d FROM DiaryEntity d WHERE d.member.id = :memberId AND (:category IS NULL OR d.category = :category) ORDER BY d.createdAt DESC")
    List<DiaryEntity> findTop10ByMemberIdAndCategory(@Param("memberId") Long memberId, @Param("category") Category category, Pageable pageable);

    @Query("SELECT d FROM DiaryEntity d WHERE d.member.id = :memberId ORDER BY d.createdAt DESC")
    DiaryEntity findTopByOrderByCreatedAtDesc(@Param("memberId") Long memberId);

    @Query("SELECT d FROM DiaryEntity d WHERE d.member.id = :memberId AND (:category IS NULL OR d.category = :category) AND LENGTH(d.title) = :minLength")
    List<DiaryEntity> findByTitleLength(@Param("memberId") Long memberId, @Param("category") Category category, @Param("minLength") int minLength);

    @Query("SELECT d FROM DiaryEntity d WHERE d.member.id = :memberId AND (:category IS NULL OR d.category = :category) ORDER BY LENGTH(d.title) DESC, d.createdAt DESC")
    List<DiaryEntity> findTop10ByMemberIdAndOrderByTitleLengthDesc(@Param("memberId") Long memberId, @Param("category") Category category, Pageable pageable);

    boolean existsByTitle(String title);

    Optional<DiaryEntity> findByIdAndMemberId(Long memberId, Long diaryId);

    @Query("SELECT COUNT(d) > 0 FROM DiaryEntity d WHERE d.member.id = :memberId AND d.title = :title")
    boolean existsByMemberIdAndTitle(@Param("memberId") Long memberId, @Param("title") String title);

}