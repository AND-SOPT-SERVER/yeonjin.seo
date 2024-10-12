package org.sopt.seminar1;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class DiaryRepository {
    private final Map<Long, String> storage = new ConcurrentHashMap<>();
    private final AtomicLong numbering = new AtomicLong();
    private final Map<Long, String> trash = new ConcurrentHashMap<>();

    void save(final Diary diary){
        final long id = numbering.addAndGet(1);
        storage.put(id, diary.getBody());
    }
    List<Diary> findAll(){
        final List<Diary> diaryList = new ArrayList<>();

        for (long index= 1; index <=numbering.longValue(); index++){
            final String body = storage.get(index);

            diaryList.add(new Diary(index, Objects.requireNonNullElse(body, "삭제된 id입니다. 삭제된 일기는 휴지통에서 확인할 수 있습니다. ")));
        }
        return diaryList;
    }


    String delete(final Long id){
        checkDiaryExists(id);
        final String deletedValue = storage.remove(id);
        addToTrash(id, deletedValue);
        return deletedValue;
    }

    String edit(final Long id, final String body){
        checkDiaryExists(id);
        return storage.put(id, body);
    }

    List<Diary> findTrashAll(){
        final List<Diary> trashList = new ArrayList<>();

        trash.forEach((id, content) -> {
            trashList.add(new Diary(id, content != null ? content : "삭제된 내용이 없습니다."));
        });

        return trashList;
    }

    String restore(final Long id){
        checkTrashExists(id);
        final String deletedValue =  trash.get(id);
        storage.put(id, deletedValue);
        trash.remove(id);
        return deletedValue;
    }


    private void checkDiaryExists(Long id) {
        if (!storage.containsKey(id)) {
            throw new NoSuchElementException("유효한 ID가 아닙니다.");
        }
    }

    private void addToTrash(final Long id, final String body){
        trash.put(id, body);
    }

    private void checkTrashExists(final Long id){
        if(!trash.containsKey(id)){
            throw new NoSuchElementException("유효한 ID가 아닙니다. ");
        }
    }
}
