package kr.co.teamA.Haru.DTO;

import kr.co.teamA.Haru.Entity.Member;
import kr.co.teamA.Haru.Entity.Place;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PlaceRecommendListDTO {
    private Long recommendNum;
    private Place place;
    private Member member;
    private String placeCdate;
}
