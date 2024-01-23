package kr.co.teamA.Haru.Repository;

import kr.co.teamA.Haru.DTO.StressDataDTO;
import kr.co.teamA.Haru.Entity.StressData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


// 스트레스 데이터 가져올 Repository
@Repository
public interface StressDataRepository extends JpaRepository<StressData, Long> {


    // 하루와 월 별과 일별 표현 가능함.
    @Query(nativeQuery = true, value =
            "select diary_data as diaryData , face_data as faceData , to_char(stress_cdate, 'YYYY-MM-DD HH24:MI') AS stressCdate, stress_score as stressScore, user_id as userId " +
                    "from stressdata " +
                    "where stress_cdate between TO_date(:startDate, 'YYYY-MM-DD') AND TO_date(:endDate , 'YYYY-MM-DD')+1 " +
                    "and user_id = :userId " +
                    "order by stress_cdate")
    List<StressDataDTO> getStressData(@Param("userId") String userId, @Param("startDate") String startDate, @Param("endDate") String endDate);

    // 연도별로 가져오기
    @Query(nativeQuery = true, value= "select diary_data as diaryData , face_data as faceData , to_char(stress_cdate, 'YYYY') AS stressCdate, stress_score as stressScore, user_id" +
            " from stressdata" +
            " where TO_CHAR(stress_cdate, 'YYYY') = :date" +
            " and user_id = :userId" +
            " order by stress_cdate")
    List<StressDataDTO> getStressDatabyYear(@Param("userId") String userId, @Param("date")String date);
}
