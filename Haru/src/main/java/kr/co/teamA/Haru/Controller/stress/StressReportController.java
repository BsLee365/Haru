package kr.co.teamA.Haru.Controller.stress;

import kr.co.teamA.Haru.DTO.StressDataDTO;
import kr.co.teamA.Haru.Entity.StressData;
import kr.co.teamA.Haru.Service.stress.StressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

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
                                @RequestParam("flag")String flag)
    {
        List<StressDataDTO> list = null;
        if (flag.equals("day")) { //하루
            System.out.println(oneday);
            list = stressService.getStressData(userId, oneday);

            if(list != null){
                return list;
            }
            return null;
        }
        else if (flag.equals("days")){ //일별 (범위)
            System.out.println(days);
            list = stressService.getStressData(userId, days);
        }
        else if (flag.equals("month")){ //월별
            System.out.println(month);

            // 월만 가져오게끔 치환
            String startMonth = month.get(0) + "-01";
            String endMonth = month.get(1) + "-01";

            System.out.println(startMonth);
            System.out.println(endMonth);

            // 추출된 값을 다시 days 변수에 설정
            month.set(0, startMonth);
            month.set(1, endMonth);

            stressService.getStressData(userId, month);

        }else if(flag.equals("year")){ // 연도별
            System.out.println(selectYear);
            stressService.getStressData(userId, selectYear);
        }
        return null;
    }
}
