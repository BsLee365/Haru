package kr.co.teamA.Haru.Controller.stress;

import kr.co.teamA.Haru.DTO.StressDataDTO;
import kr.co.teamA.Haru.Service.stress.StressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/report")
public class StressReportController {

    @Autowired
    StressService stressService;

    @PostMapping("/oneday")
    private List<StressDataDTO> returnOneday(@RequestParam("oneday") String oneday,
                              @RequestParam("days") List<String> days,
                              @RequestParam("month") List<String> month,
                              @RequestParam("selectYear") String selectYear,
                                @RequestParam("userId") String userId,
                                @RequestParam("flag")String flag) {


        List<Object> list = new ArrayList<>();
        list.add(oneday);
        list.add(days);
        list.add(month);
        list.add(selectYear);


        List<StressDataDTO> stressData = stressService.getStressData(userId, list, flag);

        for(StressDataDTO dto : stressData){
            System.out.println(dto.getStressCdate());
            System.out.println(dto.getStressScore());
        }

        return stressService.getStressData(userId, list, flag);
    }
}
