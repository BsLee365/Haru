package kr.co.teamA.Haru.Controller.home;

import org.springframework.web.bind.annotation.RestController;

import kr.co.teamA.Haru.Service.home.HomeService;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class HomeController {
    public final HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }
    
    @GetMapping("/getHomeData")
    public Map<String, Object> getHomeData() {
        Map<String, Object> data = homeService.getHomeData();
        return data;
    }
    
}
