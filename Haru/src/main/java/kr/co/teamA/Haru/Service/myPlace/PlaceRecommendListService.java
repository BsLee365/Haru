package kr.co.teamA.Haru.Service.myPlace;

import kr.co.teamA.Haru.DTO.DiaryDTO;
import kr.co.teamA.Haru.DTO.GetRecommendList;
import kr.co.teamA.Haru.DTO.RecommendItemsDTO;
import kr.co.teamA.Haru.DTO.ShowMyRecommendPlaceDTO;
import kr.co.teamA.Haru.Repository.DiaryRepository;
import kr.co.teamA.Haru.Repository.PlaceRecommendListRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class PlaceRecommendListService {
    @Autowired
    private PlaceRecommendListRepository placeRecListRepository;

    @Autowired
    private DiaryRepository diaryRepository;

    // String -> Date
    private Date ReturnDate(String targetDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate = null;
        try {
            myDate = dateFormat.parse(targetDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myDate;
    }

    // 추천 리스트 항목 가져오기
    public RecommendItemsDTO getRecommendList(GetRecommendList getRecommendListDto) {
        Date startDate = ReturnDate(getRecommendListDto.getStartdate());
        Date endDate = ReturnDate(getRecommendListDto.getEnddate());

        RecommendItemsDTO recItemDto = new RecommendItemsDTO();

        List<ShowMyRecommendPlaceDTO> recList = placeRecListRepository.getMyRecommendPlace(getRecommendListDto.getUserid(), startDate, endDate);
        List<DiaryDTO> diaryList = diaryRepository.getMyDiaryList(getRecommendListDto.getUserid(), startDate, endDate);

        recItemDto.setRec_list(recList);
        recItemDto.setDiary_list(diaryList);

        return recItemDto;
    }
}
