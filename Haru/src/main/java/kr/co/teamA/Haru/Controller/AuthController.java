package kr.co.teamA.Haru.Controller;

import kr.co.teamA.Haru.DTO.*;
import kr.co.teamA.Haru.Entity.Member;
import kr.co.teamA.Haru.security.dto.AuthenticationRequest;
import kr.co.teamA.Haru.security.dto.AuthenticationResponse;
import kr.co.teamA.Haru.security.filter.JwtTokenProvider;
import kr.co.teamA.Haru.Repository.MemberRepository;
import kr.co.teamA.Haru.Service.member.EmailSenderService;
import kr.co.teamA.Haru.Service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
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

    private Member Member;


    // 아이디 중복 확인
    @GetMapping("/{userId}/userIdCheck")
    public ResponseEntity<?> duplicationUserIdCheck(@PathVariable String userId) {
        Optional dto = memberRepository.findUserIdById(userId);
        if (dto == null) {
            return ResponseEntity.ok(1);
        } else {
            return ResponseEntity.ok(0);
        }
    }

    // 닉네임 중복 확인
    @GetMapping("/{nickname}/nicknameCheck")
    public ResponseEntity<?> duplicationNicknameCheck(@PathVariable String nickname) {
        Optional dto = memberRepository.findNicknameByNickname(nickname);
        if (dto == null) {
            return ResponseEntity.ok(1);
        } else {
            return ResponseEntity.ok(0);
        }
    }
    //  이메일 중복 확인
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

    // 이메일 인증번호 확인
    @PostMapping("/emailCheck/certification")
    public ResponseEntity<?> certification(@RequestBody EmailCheckDTO dto) {
        boolean result = emailSenderService.isVerify(dto.getEmail(), dto.getCode());
        if (result == true) {
            return ResponseEntity.ok(1);
        } else {
            return ResponseEntity.ok(0);
        }
    }

    // 회원가입
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

    // 회원정보 수정
    @PostMapping("/updateMyInfo")
    public ResponseEntity<?> updateMyInfo(@RequestBody MemberDTO dto) {
        System.out.println(dto.getEmail());
        memberService.updateMyInfo(dto);
        return ResponseEntity.ok(1);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(
            @RequestBody AuthenticationRequest authenticationRequest) {

        System.out.println("login " + authenticationRequest.getId());
        System.out.println("password : "  + authenticationRequest.getPwd());

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getId(),
                            authenticationRequest.getPwd())
            );

            // 인증 성공 시, SecurityContextHolder에 인증 정보를 설정한다.
            SecurityContextHolder.getContext().setAuthentication(authentication);
;
            String jwt = jwtTokenProvider.createToken(authentication);
            System.out.print("jwt =>"+jwt);
            return ResponseEntity.ok(new AuthenticationResponse(jwt));
        } catch
        (Exception e) {
            return ResponseEntity.ok(0);
        }

    }

    //  로그아웃
    @GetMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        // 실제로 JWT 토큰 기반 인증에서 로그아웃은 클라이언트 측에서 토큰을 삭제하는 것으로 처리되지만,
        // 서버 측에서는 SecurityContextHolder의 컨텍스트를 클리어 해야 한다.
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Logout");
    }

    // 아이디 찾기
    @PostMapping("/findById")
    public ResponseEntity<?> findById(@RequestBody FindUserIdDTO dto) {
        System.out.println(dto.getEmail());

        int check = memberService.checkFinduserId(dto);

        if (check == 1) {
            System.out.println("check" + check);
            emailSenderService.sendFindByIdEmail(dto.getEmail());
            return ResponseEntity.ok(1);
        } else {
            return ResponseEntity.ok(0);
        }
    }

    // 비밀번호 찾기
    @PostMapping("findByPwd")
    public ResponseEntity<?> findByPwd(@RequestBody FindUserPwdDTO dto) {
        System.out.println(dto.getEmail());

        int check = memberService.checkFindUserPwd(dto);

        if (check == 1) {
            System.out.println("check" + check);
            emailSenderService.sendFindByPwdEmail(dto.getEmail());
            return ResponseEntity.ok(1);
        } else {
            return ResponseEntity.ok(0);
        }
    }

    // 아이디 찾기 : 이메일 인증번호 확인
    @PostMapping("/findById/certification")
    public ResponseEntity<?> findByIdCertification(@RequestBody EmailCheckDTO dto) {
        boolean result = emailSenderService.isVerify(dto.getEmail(), dto.getCode());
        if (result == true) {
            String userId = memberRepository.findByMemberId(dto.getEmail());
            System.out.println(userId);
            return ResponseEntity.ok(userId);
        } else {
            return ResponseEntity.ok(0);
        }
    }

    // 비밀번호 찾기 : 이메일 인증번호 확인
    @PostMapping("resetPwd")
    public ResponseEntity<?> resetPwd(@RequestBody ResetPwdDTO dto) {
        System.out.println(dto);
        System.out.println(dto.getId());
        System.out.println(dto.getPwd());
        memberService.updatePassword(dto);
        return ResponseEntity.ok(1);
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
