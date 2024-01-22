package kr.co.teamA.Haru.DTO;

import kr.co.teamA.Haru.Entity.Member;
import kr.co.teamA.Haru.Entity.Place;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
// 장소 추천을 위한 DTO
public class PlaceRecommendListDTO {
    private Long recommendNum;
    private Place place;
    private Member member;
    private String placeCdate;
}
