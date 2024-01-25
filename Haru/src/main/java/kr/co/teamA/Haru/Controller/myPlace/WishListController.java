package kr.co.teamA.Haru.Controller.myPlace;

import kr.co.teamA.Haru.DTO.WishListDTO;
import kr.co.teamA.Haru.Entity.WishList;
import kr.co.teamA.Haru.Service.myPlace.WishListService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/wishList")
public class WishListController {

    @Autowired
    private final WishListService wishListService;

    // 찜하기
    @PostMapping("/getMyFavoritePlace")
    public Object[] getMyFavoritePlace(@RequestBody Map<String ,String> data) {
        String userId = data.get("id");
        wishListService.getWishListData(userId);
        return wishListService.getWishListData(userId);
    }

    // 찜 목록에 저장 - 이리수
    @PostMapping("/addWishPlace")
    public WishList saveWishPlace(@RequestBody WishListDTO wishListDTO) {
        return wishListService.saveWishPlace(wishListDTO);
    }

    // 찜 목록 삭제 - 이리수
    @PostMapping("/deleteWishPlace")
    public void deleteWishPlace(@RequestBody Map<String, Long> requestBody) {
        Long wnum = requestBody.get("wishlistnum");
        wishListService.deleteWishPlace(wnum);
    }
}