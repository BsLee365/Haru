package kr.co.teamA.Haru.Service.myPlace;

import kr.co.teamA.Haru.DTO.DiaryDTO;
import kr.co.teamA.Haru.DTO.WishListDTO;
import kr.co.teamA.Haru.Entity.Diary;
import kr.co.teamA.Haru.Entity.WishList;
import kr.co.teamA.Haru.Repository.MemberRepository;
import kr.co.teamA.Haru.Repository.WishListRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class WishListService {

    @Autowired
    private final WishListRepository wishListRepository;

    @Autowired
    private MemberRepository memberRepository;

    public Object[] getWishListData(String userId) {
        Object[] wishListDTO = new List[]{wishListRepository.findPlaceAndWishListByUserId(userId)};
        return wishListDTO;
    }

    // 찜 목록에 저장 - 이리수
    public WishList saveWishPlace(WishListDTO wishListDTO) {
        WishList wishList = new WishList();
        // member 조회 또는 생성
        memberRepository.findById(wishListDTO.getUserid()).ifPresent(wishList::setMember);
        wishList.setPlace(wishListDTO.getPlace());
        wishListRepository.save(wishList);
        return wishList;
    }

    // 찜 목록 삭제 - 이리수
    public void deleteWishPlace(Long wishlistnum) {
        wishListRepository.deleteById(wishlistnum);
    }

//    public Long getData(String userId) {
//        return wishListRepository.findById(1L);
//    }

//    public WishList<WishListDTO> getWishListData(String userId) {
//        return wishListRepository.findByUserId(userId);
//    }
}
