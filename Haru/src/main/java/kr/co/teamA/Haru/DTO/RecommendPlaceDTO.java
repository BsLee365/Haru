package kr.co.teamA.Haru.DTO;

import lombok.ToString;

// 장소 추천을 위한 RecommendPlaceDTO
public interface RecommendPlaceDTO {
    String getPlaceNum();

    String getPlaceAddress();

    String getPlaceImg();

    String getPlaceLink();

    String getPlaceName();

    String getUserId();
    int getPlaceScore();

}
