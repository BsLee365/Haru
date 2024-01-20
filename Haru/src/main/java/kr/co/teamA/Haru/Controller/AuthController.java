package kr.co.teamA.Haru.Controller;

import kr.co.teamA.Haru.security.User;
import kr.co.teamA.Haru.security.dto.AuthenticationRequest;
import kr.co.teamA.Haru.security.dto.AuthenticationResponse;
import kr.co.teamA.Haru.security.filter.JwtTokenProvider;
import kr.co.teamA.Haru.DTO.EmailCheckDTO;
import kr.co.teamA.Haru.DTO.MemberDTO;
import kr.co.teamA.Haru.Repository.MemberRepository;
import kr.co.teamA.Haru.Service.member.EmailSenderService;
import kr.co.teamA.Haru.Service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private MemberService memberService;

    @Value("${profile-img-path}")
    private String imageDirctory;

    private User user;

    @GetMapping("/{userId}/userIdCheck")
    public ResponseEntity<?> duplicationUserIdCheck(@PathVariable String userId) {
        Optional dto = memberRepository.findUserIdById(userId);
        if (dto == null) {
            return ResponseEntity.ok(1);
        } else {
            return ResponseEntity.ok(0);
        }
    }

    @GetMapping("/{nickname}/nicknameCheck")
    public ResponseEntity<?> duplicationNicknameCheck(@PathVariable String nickname) {
        Optional dto = memberRepository.findNicknameByNickname(nickname);
        if (dto == null) {
            return ResponseEntity.ok(1);
        } else {
            return ResponseEntity.ok(0);
        }
    }

    @PostMapping("emailCheck")
    public ResponseEntity<?> emailCheck(@RequestBody EmailCheckDTO dto) {
        int result = memberRepository.findUserEmailByEmail(dto.getEmail());
        if (result == 0) {
            emailSenderService.sendEmail(dto.getEmail());
            return ResponseEntity.ok(1);
        } else {
            return ResponseEntity.ok(0);
        }
    }

    @PostMapping("/emailCheck/certification")
    public ResponseEntity<?> certification(@RequestBody EmailCheckDTO dto) {
        boolean result = emailSenderService.isVerify(dto.getEmail(), dto.getCode());
        if (result == true) {
            return ResponseEntity.ok(1);
        } else {
            return ResponseEntity.ok(0);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@ModelAttribute MemberDTO dto, @RequestPart("files") List<MultipartFile> files) {
        String imgName = null;
        if (files.size() > 0) {
            for (MultipartFile multipartFile : files) {
                imgName = UUID.randomUUID() + getExtension(multipartFile.getOriginalFilename());
                String filePath = imageDirctory + imgName;
                try (FileOutputStream writer = new FileOutputStream(filePath)) {
                    writer.write(multipartFile.getBytes());
                } catch (Exception e) {
                    throw new RuntimeException("Fail to upload files.");
                }
                dto.setProfileImg(imgName);
            }
        }
        memberService.create(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(
            @RequestBody AuthenticationRequest authenticationRequest) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getId(),
                            authenticationRequest.getPwd())
            );
//            System.out.println("jwtTokenProvider =>"+jwtTokenProvider.createToken(authentication));

            // 인증 성공 시, SecurityContextHolder에 인증 정보를 설정한다.
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // JwtTokenProvider를 사용하여 JWT 토큰을 생성

            User user = new User();
            user.setUserId(authenticationRequest.getId());
            String jwt = jwtTokenProvider.createToken(authentication, user);
            System.out.print("jwt =>"+jwt);
            return ResponseEntity.ok(new AuthenticationResponse(jwt));
        } catch
        (Exception e) {
            return ResponseEntity.ok(0);
        }

    }



    @GetMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        // 실제로 JWT 토큰 기반 인증에서 로그아웃은 클라이언트 측에서 토큰을 삭제하는 것으로 처리되지만,
        // 서버 측에서는 SecurityContextHolder의 컨텍스트를 클리어 해야 한다.
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Logout");
    }


    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
