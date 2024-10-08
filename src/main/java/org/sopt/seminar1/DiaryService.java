package org.sopt.seminar1;

import java.util.List;

public class DiaryService {
    private final DiaryRepository diaryRepository = new DiaryRepository();

    void writeDiary(final String body){
        final Diary diary = new Diary(null, body);
        diaryRepository.save(diary);
    }

    List<Diary> getDiaryList() {
        return diaryRepository.findAll();
    }

    void deleteDiary(final Long id){
        final String deletedValue = diaryRepository.delete(id);
        System.out.println("삭제 완료 - " + id + " : " + deletedValue);
    }

    void editDiary(final Long id, final String body){
        final String originalBody =  diaryRepository.edit(id, body);
        System.out.println("수정 완료 - " + id + " : " + originalBody + " -> " + body);
    }
}
