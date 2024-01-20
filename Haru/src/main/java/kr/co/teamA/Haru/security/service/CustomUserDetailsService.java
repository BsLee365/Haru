package kr.co.teamA.Haru.security.service;

import kr.co.teamA.Haru.security.repo.UserRepository;
import kr.co.teamA.Haru.Repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return userRepository.findById(username)
                    .orElseThrow(() -> new Exception("user Not found "));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
