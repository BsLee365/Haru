package kr.co.teamA.Haru.Controller.myPlace;

import kr.co.teamA.Haru.DTO.GetRecommendList;
import kr.co.teamA.Haru.DTO.RecommendItemsDTO;
import kr.co.teamA.Haru.DTO.ShowMyRecommendPlaceDTO;
import kr.co.teamA.Haru.Service.myPlace.PlaceRecommendListService;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class PlaceRecommendListController {

    private final PlaceRecommendListService placeRecommendListService;
    public PlaceRecommendListController(PlaceRecommendListService placeRecommendListService) {
        this.placeRecommendListService = placeRecommendListService;
    }
    
    @PostMapping("/getRecommendList")
    public RecommendItemsDTO GetRecommendList(@RequestBody GetRecommendList getRecommendListDto) {
        return placeRecommendListService.getRecommendList(getRecommendListDto);
    }
}
