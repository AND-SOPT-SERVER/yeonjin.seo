package org.sopt.diary.service;

import org.sopt.diary.repository.DiaryEntity;
import org.sopt.diary.repository.DiaryRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class DiaryService {
    private final DiaryRepository diaryRepository;

    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    public void createDiary(String title, String body){
        DiaryEntity diaryEntity = new DiaryEntity(title, body);
        diaryRepository.save(diaryEntity);
    }

    public List<Diary> getList() {
        final List<DiaryEntity> diaryEntityList = diaryRepository.findTop10ByOrderByCreatedDateDesc();
        final List<Diary> diaryList = new ArrayList<>();

        for(DiaryEntity diaryEntity : diaryEntityList) {
            diaryList.add(
                    new Diary(diaryEntity.getId(), diaryEntity.getTitle(), diaryEntity.getBody(), diaryEntity.getCreatedDate())
            );
        }
        return diaryList;
    }

    public Diary getDiaryById(final Long dairyId) {
        final Diary diary =  diaryRepository.findById(dairyId)
                .map(diaryEntity -> new Diary(diaryEntity.getId(), diaryEntity.getTitle(), diaryEntity.getBody(), diaryEntity.getCreatedDate()))
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
}
