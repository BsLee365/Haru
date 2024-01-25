package kr.co.teamA.Haru.Service.diary;


import kr.co.teamA.Haru.DTO.DiaryDTO;
import kr.co.teamA.Haru.DTO.MemberDTO;
import kr.co.teamA.Haru.Entity.Diary;
import kr.co.teamA.Haru.Entity.Member;
import kr.co.teamA.Haru.Repository.DiaryRepository;
import kr.co.teamA.Haru.Repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;


@Service
public class DiaryService{

    @Autowired
    private DiaryRepository diaryRepository;

    @Autowired
    private MemberRepository memberRepository;

    //일기 저장
    @Transactional
    public void saveDiary(DiaryDTO diaryVO){
        // 현재 날짜 얻기
        Date currentDate = new Date();

        // Diary 생성
        Diary diary = new Diary();

        // member 조회 또는 생성
        memberRepository.findById(diaryVO.getUserId()).ifPresent(diary::setMember);

        diary.setDiaryNum(diaryVO.getDiaryNum());
        diary.setDiaryTitle(diaryVO.getDiaryTitle());
        diary.setDiaryContext(diaryVO.getDiaryContext());
        diary.setDiaryCdate(currentDate);

        diaryRepository.save(diary);
    }
    
    // 일기 업데이트 - 이리수
    public void updateDiary(DiaryDTO diaryVO) {
        Diary diary = new Diary();

        memberRepository.findById(diaryVO.getUserId()).ifPresent(diary::setMember);

        diary.setDiaryNum(diaryVO.getDiaryNum());
        diary.setDiaryTitle(diaryVO.getDiaryTitle());
        diary.setDiaryContext(diaryVO.getDiaryContext());
        diary.setDiaryCdate(diaryVO.getDiaryCdate());

        diaryRepository.save(diary);
    }

    // 일기 삭제 - 이리수
    public void deleteDiary(Long diaryNum) {
        diaryRepository.deleteById(diaryNum);
    }
}
