package kr.co.teamA.Haru.security.service;

import kr.co.teamA.Haru.DTO.MemberDTO;
import kr.co.teamA.Haru.Repository.MemberRepository;
import kr.co.teamA.Haru.Service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    // 유저 정보를 가져오는 메소드
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername ----------------------");
        System.out.println("userId => " + userId);

        UserDetails userDetails = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with userId: " + userId));

        System.out.println("userDetails => " + userDetails);
        return userDetails;
    }
}
