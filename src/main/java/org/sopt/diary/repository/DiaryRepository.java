package org.sopt.diary.repository;

import org.sopt.diary.enums.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DiaryRepository extends JpaRepository<DiaryEntity, Long> {
    @Query("SELECT d FROM DiaryEntity d WHERE (:category IS NULL OR d.category = :category) ORDER BY d.createdDate DESC")
    List<DiaryEntity> findTop10ByOrderByCreatedDateDesc(@Param("category") Category category, Pageable pageable);

    DiaryEntity findTopByOrderByCreatedDateDesc();

    @Query("SELECT d FROM DiaryEntity d WHERE (:category IS NULL OR d.category = :category) AND LENGTH(d.title) = :length ORDER BY d.createdDate DESC")
    List<DiaryEntity> findByTitleLength( @Param("category") Category category, @Param("length") int length);

    @Query("SELECT d FROM DiaryEntity d WHERE (:category IS NULL OR d.category = :category) ORDER BY LENGTH(d.title) DESC, d.createdDate DESC")
    List<DiaryEntity> findTop10ByOrderByTitleLengthDesc( @Param("category") Category category, Pageable pageable);

    boolean existsByTitle(String title);
}
