package kr.co.teamA.Haru.Controller.mypage;

import kr.co.teamA.Haru.Service.myPage.MyPageService;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MypageController {

    MyPageService myPageService;

    public MypageController(MyPageService myPageService) {
        this.myPageService = myPageService;
    }

    @PostMapping("/myPageData")
    public String myPageData(@RequestBody String token) {
        return myPageService.myPageData(token);
    }

    @PostMapping("/getMyPageData")
    public Map<String, Object> getMyPageData(@RequestParam Map<String, Object> data) {
        String userId = (String) data.get("userId");
        System.out.println("서비스 들어감");
        Map<String, Object> myPageData = myPageService.getMyPageData(userId);
        System.out.println("서비스 반환");

        return myPageData;
    }
}