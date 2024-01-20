package kr.co.teamA.Haru.security.filter;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import kr.co.teamA.Haru.DTO.MemberDTO;
import kr.co.teamA.Haru.Repository.MemberRepository;
import kr.co.teamA.Haru.security.User;
import kr.co.teamA.Haru.security.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
public class JwtTokenProvider {
    Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    private UserRepository userRepository;

    User user = new User();

    //인증된 사용자에 대한 JWT를 생성을 하고
    public String createToken(Authentication authentication, User user) {
        User userDetails = (User) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 3600000);

        Map<String, Object> claims = new HashMap<>();
        System.out.println("userDetails.getUsername() =>"+userDetails.getUserId());
        System.out.println("userDetails.getAuthorities() =>"+ user.getUserId());
        claims.put("username", userDetails.getName());
        claims.put("id", userDetails.getUserId());
        claims.put("nickname", userDetails.getNickname());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(key,SignatureAlgorithm.HS512)
                .compact();
    }


    public String resolveToken(HttpServletRequest request) {
        System.out.println("resolveToken");
        System.out.println("request.getHeader(\"Authorization\") =>"+request.getHeader("Authorization"));
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }


    public boolean validateToken(String token) {

        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token : 토큰의 형식이 올바르지 않음 ");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token : 토큰이 만료되었을 때 발생");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token : 지원되지 않는 토큰 유형일 때 발생");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty : 토큰이 비어있거나 null일 때 발생");
        } catch (SignatureException e) {
            log.error("there is an error with the signature of you token : 토큰의 서명이 유효하지 않을 때 발생");
        }

        return false;
    }

    public String getUsername(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}

