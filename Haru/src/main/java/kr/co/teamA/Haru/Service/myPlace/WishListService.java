package kr.co.teamA.Haru.Service.myPlace;

<<<<<<< HEAD
import kr.co.teamA.Haru.DTO.WishListDTO;
import kr.co.teamA.Haru.Entity.WishList;
=======
>>>>>>> Member
import kr.co.teamA.Haru.Repository.WishListRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

<<<<<<< HEAD
=======
import java.util.List;

>>>>>>> Member
@Service
@AllArgsConstructor
public class WishListService {

    @Autowired
    private final WishListRepository wishListRepository;

<<<<<<< HEAD
=======
    public Object[] getWishListData(String userId) {
        Object[] wishListDTO = new List[]{wishListRepository.findPlaceAndWishListByUserId(userId)};
        return wishListDTO;
    }

>>>>>>> Member
//    public Long getData(String userId) {
//        return wishListRepository.findById(1L);
//    }

//    public WishList<WishListDTO> getWishListData(String userId) {
//        return wishListRepository.findByUserId(userId);
//    }
}
