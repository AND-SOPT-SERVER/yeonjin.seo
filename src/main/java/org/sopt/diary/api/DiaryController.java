package org.sopt.diary.api;

import jakarta.validation.Valid;
import org.sopt.diary.enums.Category;
import org.sopt.diary.service.Diary;
import org.sopt.diary.service.DiaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/diaries")
public class DiaryController {
    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @PostMapping()
    public ResponseEntity<Map<String, String>> post(@Valid @RequestBody CreateDiaryRequest request) {
        Category category = Category.fromKorean(request.getCategory());
        diaryService.createDiary(request.getTitle(), request.getBody(), category);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "일기가 성공적으로 작성되었습니다."));
    }

    @GetMapping()
    ResponseEntity<DiaryListResponse> get(@RequestParam(required = false) String category, @RequestParam(defaultValue = "createdDate") String sortBy) {
        List<Diary> diaryList;
        Category koreanCategory = Category.fromKorean(category);
        diaryList = diaryService.getList(koreanCategory,sortBy);

        List<DiaryResponse> diaryResponsesList = new ArrayList<>();
        for(Diary diary: diaryList) {
            diaryResponsesList.add(new DiaryResponse(diary.getId(), diary.getTitle()));
        }
        return ResponseEntity.ok(new DiaryListResponse(diaryResponsesList));
    }

    @GetMapping("/{diaryId}")
    public ResponseEntity<DiaryDetailResponse> getDetail(@PathVariable String diaryId) {
        Long longDiaryId = validateId(diaryId);
        Diary diary = diaryService.getDiaryById(longDiaryId);
        System.out.println(diary.getCategory());
        DiaryDetailResponse diaryResponse = new DiaryDetailResponse(diary.getId(), diary.getTitle(), diary.getBody(), diary.getCreatedDate(), diary.getCategory());
        return ResponseEntity.ok(diaryResponse);
    }

    @PatchMapping("/{diaryId}")
    public ResponseEntity<Map<String, String>>  update(@PathVariable String diaryId, @Valid @RequestBody UpdateDiaryRequest request) {
        Long longDiaryId = validateId(diaryId);
        diaryService.updateDiary(longDiaryId, request.getBody());

        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("message", "일기를 성공적으로 수정하였습니다."));
    }

    @DeleteMapping("/{diaryId}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable String diaryId){
        Long longDiaryId = validateId(diaryId);
        diaryService.deleteDiary(longDiaryId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("message", "일기를 성공적으로 삭제하였습니다."));
    }

    private Long validateId(String id) {
        long longDiaryId;
        try {
            longDiaryId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("diary id 값이 유효하지 않습니다.");
        }
        return longDiaryId;
    }

}
