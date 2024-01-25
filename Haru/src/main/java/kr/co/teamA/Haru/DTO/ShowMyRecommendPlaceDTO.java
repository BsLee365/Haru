package kr.co.teamA.Haru.DTO;

import kr.co.teamA.Haru.Entity.Place;
import kr.co.teamA.Haru.Entity.PlaceRecommendList;
import kr.co.teamA.Haru.Entity.WishList;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class ShowMyRecommendPlaceDTO {
    private WishList wishList;
    private Place place;
    private PlaceRecommendList placeRecommendList;
}
