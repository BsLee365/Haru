package kr.co.teamA.Haru.Controller.mypage;

import kr.co.teamA.Haru.Service.myPage.MyPageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class MypageController {

    MyPageService myPageService;

    @PostMapping("/myPageData")
    public String myPageData(@RequestBody String token) {
        System.out.println("test");
        System.out.println("token => " + token);
        return myPageService.myPageData(token);
    }
}
