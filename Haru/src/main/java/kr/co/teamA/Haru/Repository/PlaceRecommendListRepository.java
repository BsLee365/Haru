package kr.co.teamA.Haru.Repository;

<<<<<<< HEAD
import kr.co.teamA.Haru.DTO.DiaryDTO;
import kr.co.teamA.Haru.DTO.ShowMyRecommendPlaceDTO;
import kr.co.teamA.Haru.Entity.PlaceRecommendList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
=======
import kr.co.teamA.Haru.Entity.PlaceRecommendList;
import kr.co.teamA.Haru.Entity.UserInterestCategory;
import org.springframework.data.jpa.repository.JpaRepository;
>>>>>>> Member
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PlaceRecommendListRepository extends JpaRepository<PlaceRecommendList, Long> {
<<<<<<< HEAD
    @Query("SELECT new kr.co.teamA.Haru.DTO.ShowMyRecommendPlaceDTO(prl.place, prl.member.userId, prl.placeCdate, prl.recommendNum) " +
            "from PlaceRecommendList prl WHERE prl.member.userId = :userId " +
            "and prl.placeCdate between :startDate and :endDate")

    List<ShowMyRecommendPlaceDTO> getMyRecommendPlace(@Param("userId") String userId,@Param("startDate") Date startDate, @Param("endDate") Date endDate);
=======

//    List<PlaceRecommendList> findByUserIdAndPlaceCdateBetween(String userId, Date startDate, Date endDate);

>>>>>>> Member
}
