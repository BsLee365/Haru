package kr.co.teamA.Haru.Service.member;

import jakarta.transaction.Transactional;
import kr.co.teamA.Haru.DTO.*;
import kr.co.teamA.Haru.Entity.Member;
import kr.co.teamA.Haru.Repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public UserInfoDTO getMemberByUserId(String userId) {
        return memberRepository.findByUserId2(userId);
    }

    public int checkPassword(String userId, String pwd) {
        String encodingPwd = passwordEncoder.encode(pwd);
        System.out.println("encodingPwd" + encodingPwd);
       String checkPassword = memberRepository.checkPassword(userId);

       return 1;
    }


    public Member create(MemberDTO dto) {
        LocalDateTime now = LocalDateTime.now();
        String encoderPassword = passwordEncoder.encode(dto.getPwd());
        System.out.println(dto.getId());
        Member entity = Member.builder()
                .userId(dto.getId())
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

    public int checkFinduserId(FindUserIdDTO dto) {
        Optional checkFindUserId = memberRepository.findUserIdByEmailAndName(dto.getEmail(), dto.getUserName());
        return checkFindUserId != null ? 1 : 0;
    }

    public int checkFindUserPwd(FindUserPwdDTO dto) {
        Optional checkFindUserPwd = memberRepository.findUserIdByEmailAndName(dto.getEmail(), dto.getUserName());
        return checkFindUserPwd != null ? 1 : 0;
    }

    public int updatePassword(ResetPwdDTO dto) {
        String encoderPassword = passwordEncoder.encode(dto.getPwd());
        memberRepository.updateByPassword(dto.getId(), encoderPassword);
        return 1;
    }


    public void updateMyInfo(MemberDTO dto) {
        memberRepository.updateByMember(dto.getId(), dto.getNickname(), dto.getEmail(), dto.getName());
    }

    public int deleteMember(String userId) {
        Member member = memberRepository.findMemberByuserId(userId);
        memberRepository.delete(member);
        System.out.println("delete member");
        return 1;
    }
}
