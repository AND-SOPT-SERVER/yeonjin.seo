package org.sopt.seminar1;


import java.util.List;

public class DiaryController {
    private Status status = Status.READY;
    private final DiaryService diaryService = new DiaryService();
    private static final int MAX_DIARY_LENGTH = 30;

    Status getStatus() {
        return status;
    }

    void boot() {
        this.status = Status.RUNNING;
    }

    void finish() {
        this.status = Status.FINISHED;
    }

    // APIS
    final List<Diary> getList() {
        return diaryService.getDiaryList();
    }

    final void post(final String body) {
        validateDiaryBody(body);
        diaryService.writeDiary(body);
    }

    final void delete(final String id) {
        final long parsedId = parseId(id);
        diaryService.deleteDiary(parsedId);
    }

    final void patch(final String id, final String body) {
        final long parsedId = parseId(id);
        validateDiaryBody(body);
        diaryService.editDiary(parsedId, body);
    }

    private void validateDiaryBody(String body) {
        if (body.length() > MAX_DIARY_LENGTH) {
            throw new IllegalArgumentException("일기의 내용을 " + MAX_DIARY_LENGTH + "자 이내로 입력해주세요.");
        }
    }

    private long parseId(String id) {
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("유효한 숫자가 아닙니다. 숫자를 입력해주세요.");
        }
    }

    enum Status {
        READY,
        RUNNING,
        FINISHED,
        ERROR,
    }
}