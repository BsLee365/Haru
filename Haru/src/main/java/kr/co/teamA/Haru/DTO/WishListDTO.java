package kr.co.teamA.Haru.DTO;

import kr.co.teamA.Haru.Entity.Place;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
// 위시리스트를 위한 DTO
public class WishListDTO {
    private String userId;
    private Place place;
}
