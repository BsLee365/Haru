package kr.co.teamA.Haru.Repository;

import kr.co.teamA.Haru.DTO.ShowMyRecommendPlaceDTO;
import kr.co.teamA.Haru.Entity.PlaceRecommendList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PlaceRecommendListRepository extends JpaRepository<PlaceRecommendList, Long> {

    // 추천 리스트 + 찜 목록
    @Query("select new kr.co.teamA.Haru.DTO.ShowMyRecommendPlaceDTO(w, p, rl) from Place p " +
            "left join WishList w on p.placeNum = w.place.placeNum " +
            "left join PlaceRecommendList rl on p.placeNum=rl.place.placeNum " +
            "where rl.member.userId=:userId " +
            "and rl.placeCdate between :startDate and :endDate " +
            "order by rl.placeCdate desc")
    List<ShowMyRecommendPlaceDTO> getMyRecommendPlace(@Param("userId") String userId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
