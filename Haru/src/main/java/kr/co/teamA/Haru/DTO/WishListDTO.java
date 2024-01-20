package kr.co.teamA.Haru.DTO;

import kr.co.teamA.Haru.Entity.Place;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WishListDTO {
    private String userId;
    private Place place;

    public WishListDTO(String userId, Place place) {
        this.userId = userId;
        this.place = place;
    }
}
