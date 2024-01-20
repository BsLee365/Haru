package kr.co.teamA.Haru.Controller.myPlace;

import kr.co.teamA.Haru.Service.myPlace.WishListService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/wishList")
public class WishListController {

    @Autowired
    private final WishListService wishListService;

    @PostMapping("/getMyFavoritePlace")
    public Object[] getMyFavoritePlace(@RequestBody Map<String ,String> data) {
        String userId = data.get("id");
        System.out.println(userId);

        wishListService.getWishListData(userId);
        System.out.println(wishListService.getWishListData(userId));

        return wishListService.getWishListData(userId);
    }
}



