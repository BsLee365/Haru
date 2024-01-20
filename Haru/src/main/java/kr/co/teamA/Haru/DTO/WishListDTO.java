package kr.co.teamA.Haru.DTO;

<<<<<<< HEAD
=======
import kr.co.teamA.Haru.Entity.Place;
>>>>>>> Member
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
<<<<<<< HEAD
@AllArgsConstructor
@NoArgsConstructor
public class WishListDTO {
    private Long wishListNum;
    private String userId;
    private String placeNumber;
=======
@NoArgsConstructor
public class WishListDTO {
    private String userId;
    private Place place;

    public WishListDTO(String userId, Place place) {
        this.userId = userId;
        this.place = place;
    }
>>>>>>> Member
}
