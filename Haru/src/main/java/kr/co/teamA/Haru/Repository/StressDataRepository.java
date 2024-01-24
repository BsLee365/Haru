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


    // 하루를 가져옴. 하루 시간대 별로 구분.
    @Query(nativeQuery = true, value =
            "select diary_data/10 as diaryData , face_data/10 as faceData , to_char(stress_cdate, 'YYYY-MM-DD HH24:MI') AS stressCdate, stress_score as stressScore, user_id as userId " +
                    "from stressdata " +
                    "where stress_cdate between TO_date(:startDate , 'YYYY-MM-DD') AND TO_date(:endDate , 'YYYY-MM-DD')+1 " +
                    "and user_id = :userId " +
                    "order by stress_cdate")
    List<StressDataDTO> getStressDatabyDay(@Param("userId") String userId, @Param("startDate") String startDate, @Param("endDate") String endDate);


    // 일간, 일자별로만 가져옴.
    @Query(nativeQuery = true, value="select round(avg(face_data)/10, 2) as faceData, round(avg(diary_data)/10,2) as diaryData, " +
            "round(avg(stress_score) 2) as stressScore, to_char(stress_cdate, 'YYYY-MM-DD') as stressCdate, user_id as userId " +
            "from stressdata " +
            "where stress_cdate Between To_date(:startDate, 'YYYY-MM-DD') and to_date(:endDate, 'YYYY-MM-DD') " +
            "and user_id = :userId " +
            "group by to_char(stress_cdate, 'YYYY-MM-DD'), user_id " +
            "order by stressCdate")
    List<StressDataDTO> getStressDatabyDays(@Param("userId")String userId, @Param("startDate") String startDate, @Param("endDate") String endDate);


    // 월 별로 가져오기
    @Query(nativeQuery = true, value="select round(avg(face_data)/10, 2) as faceData, round(avg(diary_data)/10,2) as diaryData, " +
            "round(avg(stress_score), 2) as stressScore, to_char(stress_cdate, 'YYYY-MM') as stressCdate, user_id as userId " +
            "from stressdata " +
            "where stress_cdate Between to_date(:startDate, 'YYYY-MM') and to_date(:endDate, 'YYYY-MM')+1 " +
            "and user_id = :userId " +
            "group by to_char(stress_cdate, 'YYYY-MM'), user_id " +
            "order by stressCdate")
    List<StressDataDTO> getStressDatabyMonth(@Param("userId")String userId, @Param("startDate") String startDate, @Param("endDate") String endDate);


    // 1년치 가져오기
    @Query(nativeQuery = true, value= "select round(avg(diary_data)/10, 2) as diaryData, " +
            "round(avg(face_data)/10, 2) as faceData," +
            "to_char(stress_cdate, 'YYYY-MM') AS stressCdate, " +
            "round(avg(stress_score), 2) as stressScore " +
            "from stressdata " +
            "where TO_CHAR(stress_cdate, 'YYYY') = :date " +
            "and user_id = :userId " +
            "group by to_char(stress_cdate, 'YYYY-MM') " +
            "order by stressCdate")
    List<StressDataDTO> getStressDatabyYear(@Param("userId") String userId, @Param("date")String date);


}
