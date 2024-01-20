package kr.co.teamA.Haru.DTO;

import kr.co.teamA.Haru.Entity.Place;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class ShowMyRecommendPlaceDTO {
    private Place place;
    private String user_id;
    private Date placeCdate;
    private Long recommendNum;
}
