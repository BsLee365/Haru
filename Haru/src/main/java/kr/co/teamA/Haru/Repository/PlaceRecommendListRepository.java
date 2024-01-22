package kr.co.teamA.Haru.Repository;

import kr.co.teamA.Haru.Entity.PlaceRecommendList;
import kr.co.teamA.Haru.Entity.UserInterestCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PlaceRecommendListRepository extends JpaRepository<PlaceRecommendList, Long> {

//    List<PlaceRecommendList> findByUserIdAndPlaceCdateBetween(String userId, Date startDate, Date endDate);

}
