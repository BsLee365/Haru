package kr.co.teamA.Haru.Repository;

import kr.co.teamA.Haru.DTO.DiaryDTO;
import kr.co.teamA.Haru.Entity.Diary;
import kr.co.teamA.Haru.Entity.PlaceRecommendList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    @Query("SELECT new kr.co.teamA.Haru.DTO.DiaryDTO(diary.diaryNum, diary.member.userId, diary.diaryTitle, diary.diaryContext, diary.diaryCdate) " +
            "from Diary diary WHERE diary.member.userId = :userId " +
            "and diary.diaryCdate between :startDate and :endDate")
    List<DiaryDTO> getMyDiaryList(@Param("userId") String userId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
