package kr.co.teamA.Haru.Controller.member;

import kr.co.teamA.Haru.DTO.MemberDTO;
import kr.co.teamA.Haru.Service.member.EmailSenderService;
import kr.co.teamA.Haru.Service.member.MemberService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.util.List;
import java.util.UUID;

@RestController
public class MemberController {

    @Value("${profile-img-path}")
    private String imageDirctory;

    private final MemberService memberService;

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
            String filePath = imageDirctory + imgName;
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
}

