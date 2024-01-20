package kr.co.teamA.Haru.Repository;

import kr.co.teamA.Haru.DTO.RecommendPlaceDTO;
import kr.co.teamA.Haru.Entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// 장소추천 레파지토리
@Repository
public interface RecommendPlaceRepository extends JpaRepository<Place, Long> {


    // 사용자가 선택한 카테고리 기반 추천
    @Query(nativeQuery = true, value = "select a.place_num as placeNum , a.place_address as placeAddress, a.place_img as placeImg, a.place_link as placeLink," +
            " a.place_name as placeName, b.user_id as userId" +
            " from place a" +
            " join (select a.main_category, a.user_id, a.interest_category_num , b.sub_category" +
            " from (select b.main_category, a.user_id, a.interest_category_num" +
            " from (select a.user_id, b.interest_category_num, b.main_category" +
            " from member a join userinterestcategory b" +
            " on a.user_id = b.user_id " +
            " where a.user_id = :userId) a join maincategory b" +
            " on a.main_category = b.main_category) a join subcategory b" +
            " on a.main_category = b.main_category) b" +
            " on a.sub_category = b.sub_category")
    List<RecommendPlaceDTO> recommendQuery(@Param("userId") String userId);

    // 사용자가 찜한 카테고리 기반 추천
    @Query(nativeQuery = true, value= "select distinct p.place_num as placeNum, p.place_address as placeAddress, p.place_img as placeImg, " +
            "p.place_link as placeLink, p.place_name as placeName, c.user_id as userId " +
            "from place p join (select p.sub_category, t.user_id " +
            "from place p join " +
            "(select m.user_id, w.wish_list_num, w.place_num " +
            "from member m join wishlist w " +
            "on m.user_id = w.user_id " +
            "where m.user_id = :userId) t " +
            "on t.place_num = p.place_num) c " +
            "on p.sub_category = c.sub_category")
    List<RecommendPlaceDTO> recommendDibs(@Param("userId") String userId);

    // 사용자가 스트레스 수치 기반 장소 추천




}
