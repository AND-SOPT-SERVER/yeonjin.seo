package org.sopt.seminar1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class DiaryRepository {
    private final Map<Long, String> storage = new ConcurrentHashMap<>();
    private final AtomicLong numbering = new AtomicLong();

    void save(final Diary diary){
        final long id = numbering.addAndGet(1);
        storage.put(id, diary.getBody());
    }

    List<Diary> findAll(){
        final List<Diary> diaryList = new ArrayList<>();

        for (long index= 1; index <=numbering.longValue(); index++){
            final String body = storage.get(index);
            diaryList.add(new Diary(index, body));
        }
        return diaryList;
    }

    String delete(final long id){
        checkDiaryExists(id);
        final String deletedValue =  storage.get(id);
        final long lastIndex = numbering.longValue();

        for (long index=id+1; index <= lastIndex; index++){
            final String newValue = storage.get(index);
            storage.put(index-1, newValue);
        }

        storage.remove(lastIndex);
        numbering.decrementAndGet();
        return deletedValue;
    }

    String edit(final long id, final String body){
        checkDiaryExists(id);
        return storage.put(id, body);
    }

    private void checkDiaryExists(long id) {
        if (!storage.containsKey(id)) {
            throw new NoSuchElementException("유효한 ID가 아닙니다.");
        }
    }
}
