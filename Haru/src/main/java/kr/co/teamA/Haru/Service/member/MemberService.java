package kr.co.teamA.Haru.Service.member;

import kr.co.teamA.Haru.DTO.MemberDTO;
import kr.co.teamA.Haru.Entity.Member;
import kr.co.teamA.Haru.Repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MemberService{

    private final MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

//    public int checkDuplicateUserId(String userId) {
//        Optional checkUserId = memberRepository.findUserIdById(userId);
//        return checkUserId != null ? 1 : 0;
//    }

    public Member create(MemberDTO dto) {
        LocalDateTime now = LocalDateTime.now();
        String encoderPassword = passwordEncoder.encode(dto.getPwd());
        System.out.println("encodeing" + encoderPassword);
        System.out.println(dto.getUserId());
        Member entity = Member.builder()
                .userId(dto.getUserId())
                .pwd(encoderPassword)
                .nickname(dto.getNickname())
                .name(dto.getName())
                .email(dto.getEmail())
                .profileImg(dto.getProfileImg())
                .cDate(now.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .role("USER")
                .build();
        return memberRepository.save(entity);
    }

    public int checkDuplicateNickname(String nickname) {
        Optional checkNickname = memberRepository.findNicknameByNickname(nickname);
        return checkNickname != null ? 1 : 0;
    }

}
