package kr.co.teamA.Haru.Repository;

import io.lettuce.core.dynamic.annotation.Param;
import kr.co.teamA.Haru.Entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WishListRepository extends JpaRepository<WishList, Long> {

    // 유저 아이디로 찜 목록 가져오기
    @Query("SELECT p.placeImg, p.placeLink, p.placeName, p.placeScore, p.placeAddress FROM Place p JOIN WishList w ON p.placeNum = w.place.placeNum WHERE w.member.userId = :userId")
    List<Object[]> findPlaceAndWishListByUserId(@Param("userId") String userId);
}
