package kr.co.teamA.Haru.Controller.myPlace;

import kr.co.teamA.Haru.DTO.WishListDTO;
import kr.co.teamA.Haru.Entity.WishList;
import kr.co.teamA.Haru.Service.myPlace.WishListService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/wishList")
public class WishListController {

    @Autowired
    private final WishListService wishListService;

    @PostMapping("/getMyFavoritePlace")
    public String getMyFavoritePlace(@RequestBody Map<String ,String> data) {
        String userId = data.get("id");
        System.out.println(userId);

//        Long placeNumber = wishListService.getData(userId);
//        List<WishListDTO> list = wishListService.getWishListData(userId);
//        System.out.println(list);
//        return list;
        return "test";
    }
}



