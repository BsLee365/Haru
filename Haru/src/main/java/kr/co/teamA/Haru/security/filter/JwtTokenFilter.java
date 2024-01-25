package kr.co.teamA.Haru.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;

    private UserDetailsService userDetailsService;


    // 토큰을 검증하는 필터
    // JWT(JSON Web Token)를 사용하여 사용자의 인증 정보를 검증하고, 검증이 성공하면 Spring Security의 컨텍스트에 해당 사용자 정보를 설정
    // 각 HTTP 요청이 들어올 때마다 JWT를 검증하고, 유효한 경우 해당 사용자를 Spring Security 컨텍스트에 설정하여 보안 인증을 수행
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // JwtTokenProvider를 사용하여 추출된 토큰이 유효한지 확인
        String token = jwtTokenProvider.resolveToken(request);
        System.out.println("token doFilterInternal =>"+token);
        if (token != null && jwtTokenProvider.validateToken(token)) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(
                    jwtTokenProvider.getUserId(token));
        //     System.out.println("userDetails => "+userDetails);

            // Spring Security 컨텍스트 설정: 유효한 토큰과 사용자 정보를 기반으로 UsernamePasswordAuthenticationToken을 생성
            // Spring Security의 컨텍스트에 설정 => 현재 사용자로 인증된 상태가 됨
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
        //     System.out.println("authentication => "+authentication);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        
        // 다음 필터로 전달
        filterChain.doFilter(request, response);
    }
}