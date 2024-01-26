package kr.co.teamA.Haru.Controller.myPlace;

import kr.co.teamA.Haru.DTO.GetRecommendListDTO;
import kr.co.teamA.Haru.DTO.RecommendItemsDTO;
import kr.co.teamA.Haru.DTO.ShowMyRecommendPlaceDTO;
import kr.co.teamA.Haru.Service.myPlace.PlaceRecommendListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recommend")
public class PlaceRecommendListController {

    @Autowired
    private final PlaceRecommendListService placeRecommendListService;
    public PlaceRecommendListController(PlaceRecommendListService placeRecommendListService) {
        this.placeRecommendListService = placeRecommendListService;
    }
    
    @PostMapping("/getRecommendList")
    public RecommendItemsDTO GetRecommendList(@RequestBody GetRecommendListDTO getRecommendListDto) {
        return placeRecommendListService.getRecommendList(getRecommendListDto);
    }

    @PostMapping("/getMyRecList")
    public List<ShowMyRecommendPlaceDTO> getMyRecList(@RequestBody GetRecommendListDTO getRecommendListDto) {
        return placeRecommendListService.getMyRecList(getRecommendListDto);
    }
}
