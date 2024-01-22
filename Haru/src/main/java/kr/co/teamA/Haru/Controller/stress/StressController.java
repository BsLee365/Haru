package kr.co.teamA.Haru.Controller.stress;

import kr.co.teamA.Haru.Service.stress.StressService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class StressController {

    StressService stressService;
    @PostMapping("/stress")
    public String stress(@RequestBody String token) {
        stressService.tokenValidation(token);
        return "stress";
    }
}
