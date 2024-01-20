package kr.co.teamA.Haru.Service.myPlace;

import kr.co.teamA.Haru.Repository.PlaceRecommendListRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class PlaceRecommendListService {

    @Autowired
    private final PlaceRecommendListRepository placeRecommendList;

//    public Long getData(String userId, Date startDate, Date endDate) {
//        return placeRecommendList.findByUserIdAndPlaceCdateBetween(userId, startDate, endDate);
//    }

//    public WishList<WishListDTO> getWishListData(String userId) {
//        return wishListRepository.findByUserId(userId);
//    }
}
