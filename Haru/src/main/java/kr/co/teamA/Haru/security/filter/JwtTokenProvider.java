package kr.co.teamA.Haru.security.filter;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import kr.co.teamA.Haru.Entity.Member;
import kr.co.teamA.Haru.Repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtTokenProvider {
    Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);


    //인증된 사용자에 대한 JWT를 생성을 하고 검증
    // 토큰 생성
    public String createToken(Authentication authentication) {
        System.out.println("createToken--------------------------");
        Member userDetails = (Member) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 3600000);

        Map<String, Object> claims = new HashMap<>();
//        System.out.println("userDetails.getUsername() =>"+userDetails.getUserId());
//        System.out.println("userDetails.getAuthorities() =>"+ userDetails.getUserId());
        // 닉네임, 아이디, 이름 토큰에 저장 설정
        claims.put("username", userDetails.getName());
        claims.put("id", userDetails.getUserId());
        claims.put("nickname", userDetails.getNickname());

        System.out.println("key =>"+key);

        // 토큰 생성
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(key,SignatureAlgorithm.HS512)
                .compact();
    }

    // 토큰에서 회원 정보 추출. 토큰 가져오기
    public String resolveToken(HttpServletRequest request) {
        System.out.println("resolveToken--------------------------");
        System.out.println("request.getHeader(\"Authorization\") =>"+request.getHeader("Authorization"));
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }


    // 토큰 유효성 검사
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
    // 토큰에서 회원 아이디 추출
    public String getUserId(String token) {
        System.out.println("getUserId : " + token);

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("id").toString();
    }
}