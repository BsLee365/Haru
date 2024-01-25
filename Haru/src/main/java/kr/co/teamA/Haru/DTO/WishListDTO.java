package kr.co.teamA.Haru.DTO;

import kr.co.teamA.Haru.Entity.Place;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WishListDTO {
    private Long wishListNum;
    private String userid;
    private Place place;
}
