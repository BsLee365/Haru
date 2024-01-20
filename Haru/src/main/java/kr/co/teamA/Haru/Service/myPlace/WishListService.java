package kr.co.teamA.Haru.Service.myPlace;

import kr.co.teamA.Haru.Repository.WishListRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class WishListService {

    @Autowired
    private final WishListRepository wishListRepository;

    public Object[] getWishListData(String userId) {
        Object[] wishListDTO = new List[]{wishListRepository.findPlaceAndWishListByUserId(userId)};
        return wishListDTO;
    }

//    public Long getData(String userId) {
//        return wishListRepository.findById(1L);
//    }

//    public WishList<WishListDTO> getWishListData(String userId) {
//        return wishListRepository.findByUserId(userId);
//    }
}
