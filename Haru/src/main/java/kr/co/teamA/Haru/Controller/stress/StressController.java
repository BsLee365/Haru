package kr.co.teamA.Haru.Controller.stress;


import kr.co.teamA.Haru.DTO.PersonalStressDTO;
import kr.co.teamA.Haru.DTO.RecommendPlaceDTO;
import kr.co.teamA.Haru.DTO.StressSaveDTO;
import kr.co.teamA.Haru.Service.stress.StressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stress")
public class StressController {

    @Autowired
    private StressService stressService;

    @PostMapping("/")
    public String stress(@RequestBody String token) {
        stressService.tokenValidation(token);
        return "stress";
    }

    // 장소 추천 알고리즘 컨트롤러
    @PostMapping("/recommend")
    @ResponseBody
    public List<RecommendPlaceDTO> recommendPlace(@RequestBody PersonalStressDTO stressDTO){
        System.out.println(stressDTO.getUserid());
        System.out.println("스트레스 수치 받음.");

        //스트레스 저장 비즈니스 로직
        return stressService.getRecommendPlaces(stressDTO.getUserid());
    }

    // 스트레스 저장 컨트롤러
    @PostMapping("/saveStress")
    public void saveStress(@RequestBody StressSaveDTO stressSaveDTO){

        stressService.saveStressData(stressSaveDTO);
    }
}
