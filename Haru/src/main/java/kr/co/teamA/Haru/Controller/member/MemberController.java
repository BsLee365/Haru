package kr.co.teamA.Haru.Controller.member;

import kr.co.teamA.Haru.DTO.MemberDTO;
import kr.co.teamA.Haru.DTO.UserInfoDTO;
import kr.co.teamA.Haru.Service.member.EmailSenderService;
import kr.co.teamA.Haru.Service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Value("${profile-img-path}")
    private String imageDirectory;

    public MemberController(MemberService memberService, EmailSenderService mailSender) {
        this.memberService = memberService;
    }

    @GetMapping("/{userId}/userId")
    public int getUserId(@PathVariable("userId") String userId) {
        return memberService.checkDuplicateNickname(userId);
    }

    @GetMapping("/{nickname}/nicknameCheck")
    public int getNickname(@PathVariable("nickname") String nickname) {
        int returnVal = memberService.checkDuplicateNickname(nickname);
        return returnVal;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> handleFileUpload(@ModelAttribute MemberDTO memberDTO,
            @RequestParam("files") List<MultipartFile> files) {

        String imgName = null;
        for (MultipartFile multipartFile : files) {
            imgName = UUID.randomUUID() + getExtension(multipartFile.getOriginalFilename());
            String filePath = imageDirectory + imgName;
            try (FileOutputStream writer = new FileOutputStream(filePath)) {
                writer.write(multipartFile.getBytes());
            } catch (Exception e) {
                throw new RuntimeException("Fail to upload files.");
            }
        }
        memberDTO.setProfileImg(imgName);
        memberService.create(memberDTO);
        return ResponseEntity.ok().build();
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    // 개인 정보 수정 시 비밀번호 확인
    @PostMapping("/userConfirm")
    public int userConfirm(@RequestBody Map<String, String> userData) {
        System.out.println(userData.get("userId"));
        System.out.println(userData.get("password"));

        int check = memberService.checkPassword(userData.get("userId"), userData.get("password"));
        return check;
    }

    // 개인 정보 수정
    @PostMapping("/userData")
    public UserInfoDTO getUserData(@RequestBody Map<String, String> userData) {
        System.out.println(userData.get("id"));
        UserInfoDTO memberDTO = memberService.getMemberByUserId(userData.get("id"));
        return memberDTO;
    }

    // 개인 정보 삭제
    @DeleteMapping("/delete/{userId}")
    public int deleteMember(@PathVariable String userId) {
        System.out.println(userId);
        int result = memberService.deleteMember(userId);
        return result;
    }

    @PostMapping("/UpdateProfileImg")
    public String UpdateProfileImg(@RequestParam("userId") String userId, @RequestParam("file") MultipartFile img) {
        String rootPath = System.getProperty("user.dir");
        String imgName = UUID.randomUUID() + getExtension(img.getOriginalFilename());
        String filePath = rootPath + imageDirectory + imgName;
        System.out.println(filePath);
        try (FileOutputStream writer = new FileOutputStream(filePath)) {
            writer.write(img.getBytes());
        } catch (Exception e) {
            throw new RuntimeException("Fail to upload files.");
        }
        memberService.updateProfileImg(userId, imgName);
        return "success";
    }

}
