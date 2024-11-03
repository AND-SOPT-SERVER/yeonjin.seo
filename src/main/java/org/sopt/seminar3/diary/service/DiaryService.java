package org.sopt.seminar3.diary.service;


import org.sopt.seminar3.diary.enums.Category;
import org.sopt.seminar3.diary.repository.DiaryEntity;
import org.sopt.seminar3.diary.repository.DiaryRepository;
import org.sopt.seminar3.member.repository.MemberEntity;
import org.sopt.seminar3.member.repository.MemberRepository;
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
    private final MemberRepository memberRepository;
    private static final Duration DIARY_WRITE_COOLDOWN = Duration.ofMinutes(5);

    public DiaryService(DiaryRepository diaryRepository, MemberRepository memberRepository) {
        this.diaryRepository = diaryRepository;
        this.memberRepository = memberRepository;
    }

    public void createDiary(final Long memberId, final String title, final String body, final Category category){
        MemberEntity member = validateMemberId(memberId);

        LocalDateTime now = LocalDateTime.now();
        DiaryEntity latestDiary = diaryRepository.findTopByOrderByCreatedAtDesc(memberId);

        if (latestDiary != null) {
            Duration timeSinceLastDiary = Duration.between(latestDiary.getCreatedAt(), now);
            if (timeSinceLastDiary.compareTo(DIARY_WRITE_COOLDOWN) < 0) {
                throw new IllegalStateException("5분 후에 다시 일기를 작성할 수 있습니다. 잠시 후에 다시 시도해주세요. ");
            }
        }

        if (diaryRepository.existsByMemberIdAndTitle(memberId, title)) {
            throw new IllegalArgumentException("이미 있는 제목입니다. 제목을 수정해주세요. ");
        }

        DiaryEntity diaryEntity = new DiaryEntity(member, title, body, category);
        diaryRepository.save(diaryEntity);
    }

    public List<Diary> getList(final Long memberId, final Category category, final String sortBy) {
        validateMemberId(memberId);
        if ("titleLength".equals(sortBy)) {
            return this.getTenDiaryListSortedByTitleLength(memberId, category);
        } else {
            return this.getTenDiaryListSorted(memberId, category);
        }
    }

    public Diary getDiaryById(final Long memberId, final Long dairyId) {
        validateMemberId(memberId);
        return diaryRepository.findByIdAndMemberId(memberId, dairyId)
                .map(diaryEntity -> new Diary(diaryEntity.getId(), diaryEntity.getTitle(), diaryEntity.getBody(), diaryEntity.getCreatedAt(), diaryEntity.getCategory()))
                .orElseThrow(() -> new NoSuchElementException("해당하는 ID의 일기가 없습니다."));
    }

    public void updateDiary(final Long memberId, final Long diaryId, final String body){
        validateMemberId(memberId);
        final DiaryEntity diaryEntity = diaryRepository.findByIdAndMemberId(memberId,diaryId)
                .orElseThrow(() -> new NoSuchElementException("해당하는 ID의 일기가 없습니다."));

        diaryEntity.setBody(body);
        diaryRepository.save(diaryEntity);
    }

    public void deleteDiary(final Long memberId, final Long diaryId){
        validateMemberId(memberId);
        final DiaryEntity diaryEntity = diaryRepository.findByIdAndMemberId(memberId,diaryId)
                .orElseThrow(() -> new NoSuchElementException("해당 일기가 존재하지 않습니다."));

        diaryRepository.delete(diaryEntity);
    }

    private List<Diary> getTenDiaryListSortedByTitleLength(final Long memberId, final Category category) {
        List<DiaryEntity> topDiaries = diaryRepository.findTop10ByMemberIdAndOrderByTitleLengthDesc(memberId, category, PageRequest.of(0, 10));

        if (topDiaries.isEmpty()) {
            return Collections.emptyList();
        }

        int minLength = topDiaries.get(topDiaries.size() - 1).getTitle().length();
        List<Diary> diaryList = new ArrayList<>();
        for (DiaryEntity diaryEntity: topDiaries) {
            if (diaryEntity.getTitle().length() != minLength){
                diaryList.add(
                        new Diary(diaryEntity.getId(), diaryEntity.getTitle(), diaryEntity.getBody(), diaryEntity.getCreatedAt(), diaryEntity.getCategory())
                );
            }
        }

        List<DiaryEntity> diariesWithMaxLength = diaryRepository.findByTitleLength(memberId, category, minLength);

        for (DiaryEntity diaryEntity : diariesWithMaxLength) {
            diaryList.add(
                    new Diary(diaryEntity.getId(), diaryEntity.getTitle(), diaryEntity.getBody(), diaryEntity.getCreatedAt(), diaryEntity.getCategory())
            );
        }

        return diaryList;
    }

    private List<Diary> getTenDiaryListSorted(final Long memberId, final Category category) {
        final List<DiaryEntity> diaryEntityList = diaryRepository.findTop10ByMemberIdAndCategory(memberId, category, PageRequest.of(0, 10));
        final List<Diary> diaryList = new ArrayList<>();

        for(DiaryEntity diaryEntity : diaryEntityList) {
            diaryList.add(
                    new Diary(diaryEntity.getId(), diaryEntity.getTitle(), diaryEntity.getBody(), diaryEntity.getCreatedAt(), diaryEntity.getCategory())
            );
        }
        return diaryList;
    }

    private MemberEntity validateMemberId(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }
}