package kr.co.teamA.Haru.DTO;

import kr.co.teamA.Haru.Entity.Place;
<<<<<<< HEAD
import lombok.*;
=======
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
>>>>>>> yj_base

@Getter
@Setter
@NoArgsConstructor
<<<<<<< HEAD
@AllArgsConstructor
@ToString
public class WishListDTO {
    private Long wishListNum;
    private String userid;
=======
// 위시리스트를 위한 DTO
public class WishListDTO {
    private String userId;
>>>>>>> yj_base
    private Place place;
}
