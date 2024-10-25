package org.sopt.diary.service;

import org.sopt.diary.enums.Category;
import org.sopt.diary.repository.DiaryEntity;
import org.sopt.diary.repository.DiaryRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private static final Duration DIARY_WRITE_COOLDOWN = Duration.ofMinutes(5);

    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    public void createDiary(String title, String body, Category category){
        LocalDateTime now = LocalDateTime.now();
        DiaryEntity latestDiary = diaryRepository.findTopByOrderByCreatedDateDesc();

        if (latestDiary != null) {
            Duration timeSinceLastDiary = Duration.between(latestDiary.getCreatedDate(), now);
            if (timeSinceLastDiary.compareTo(DIARY_WRITE_COOLDOWN) < 0) {
                throw new IllegalStateException("5분 후에 다시 일기를 작성할 수 있습니다. 잠시 후에 다시 시도해주세요. ");
            }
        }

        if (diaryRepository.existsByTitle(title)) {
            throw new IllegalArgumentException("이미 있는 제목입니다. 제목을 수정해주세요. ");
        }

        DiaryEntity diaryEntity = new DiaryEntity(title, body, category);
        diaryRepository.save(diaryEntity);
    }

    public List<Diary> getList(Category category, String sortBy) {
        if ("titleLength".equals(sortBy)) {
            return this.getTenDiaryListSortedByTitleLength(category);
        } else {
            return this.getTenDiaryListSorted(category);
            }
    }

    public Diary getDiaryById(final Long dairyId) {
        final Diary diary =  diaryRepository.findById(dairyId)
                .map(diaryEntity -> new Diary(diaryEntity.getId(), diaryEntity.getTitle(), diaryEntity.getBody(), diaryEntity.getCreatedDate(), diaryEntity.getCategory()))
                .orElse(null);

        if (diary == null) {
            throw new NoSuchElementException("해당하는 ID의 일기가 없습니다.");
        }

        return diary;
    }

    public void updateDiary(final Long diaryId, final String body){
        final DiaryEntity diaryEntity = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new NoSuchElementException("해당하는 ID의 일기가 없습니다."));

        diaryEntity.setBody(body);
        diaryRepository.save(diaryEntity);
    }

    public void deleteDiary(final Long diaryId){
        if (!diaryRepository.existsById(diaryId)) {
            throw new NoSuchElementException("해당 일기가 존재하지 않습니다.");
        }
        diaryRepository.deleteById(diaryId);
    }

    private List<Diary> getTenDiaryListSortedByTitleLength(Category category) {
        List<DiaryEntity> topDiaries = diaryRepository.findTop10ByOrderByTitleLengthDesc(category, PageRequest.of(0, 10));

        if (topDiaries.isEmpty()) {
            return Collections.emptyList();
        }

        int minLength = topDiaries.get(topDiaries.size() - 1).getTitle().length();

        List<Diary> diaryList = new ArrayList<>();
        for (DiaryEntity diaryEntity: topDiaries) {
            if (diaryEntity.getTitle().length() != minLength){
                diaryList.add(
                        new Diary(diaryEntity.getId(), diaryEntity.getTitle(), diaryEntity.getBody(), diaryEntity.getCreatedDate(), diaryEntity.getCategory())
                );
            }
        }

        List<DiaryEntity> diariesWithMaxLength = diaryRepository.findByTitleLength(category, minLength);

        for (DiaryEntity diaryEntity : diariesWithMaxLength) {
            diaryList.add(
                    new Diary(diaryEntity.getId(), diaryEntity.getTitle(), diaryEntity.getBody(), diaryEntity.getCreatedDate(), diaryEntity.getCategory())
            );
        }

        return diaryList;
    }

    private List<Diary> getTenDiaryListSorted(Category category) {
        final List<DiaryEntity> diaryEntityList = diaryRepository.findTop10ByOrderByCreatedDateDesc(category, PageRequest.of(0, 10));
        final List<Diary> diaryList = new ArrayList<>();

        for(DiaryEntity diaryEntity : diaryEntityList) {
            diaryList.add(
                    new Diary(diaryEntity.getId(), diaryEntity.getTitle(), diaryEntity.getBody(), diaryEntity.getCreatedDate(), diaryEntity.getCategory())
            );
        }
        return diaryList;

    }
}
