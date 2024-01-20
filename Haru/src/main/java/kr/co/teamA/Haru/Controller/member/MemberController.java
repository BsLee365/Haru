package kr.co.teamA.Haru.Controller.member;

import kr.co.teamA.Haru.DTO.MemberDTO;
import kr.co.teamA.Haru.DTO.UserInfoDTO;
import kr.co.teamA.Haru.Service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;
    @PostMapping("/userConfirm")
    public int userConfirm(@RequestBody Map<String, String> userData) {
        System.out.println(userData.get("userId"));
        System.out.println(userData.get("password"));

        int check = memberService.checkPassword(userData.get("userId"), userData.get("password"));
        return check;
    }

    @PostMapping("/userData")
    public UserInfoDTO getUserData(@RequestBody Map<String, String> userData) {
        System.out.println(userData.get("id"));
        UserInfoDTO memberDTO = memberService.getMemberByUserId(userData.get("id"));
        return memberDTO;
    }

    @DeleteMapping("/delete/{userId}")
    public int deleteMember(@PathVariable String userId) {
        System.out.println(userId);
        int result = memberService.deleteMember(userId);
        return result;
    }
}
