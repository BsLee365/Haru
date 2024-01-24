package kr.co.teamA.Haru;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@RequiredArgsConstructor
@Repository
public class CertificationNumberDAO {
    private final StringRedisTemplate stringRedisTemplate;

<<<<<<< HEAD
=======
    //
>>>>>>> yj_base
    public void saveCertificationNumber(String email, String authCode) {
        stringRedisTemplate.opsForValue().set(email, authCode, Duration.ofSeconds(10000));
    }

<<<<<<< HEAD
=======
    // 이메일 인증번호 가져오기
>>>>>>> yj_base
    public String getCertificationNumber(String email) {
        return stringRedisTemplate.opsForValue().get(email);
    }

<<<<<<< HEAD
=======
    // 이메일 인증번호 삭제
>>>>>>> yj_base
    public void deleteCertificationNumber(String email) {
        stringRedisTemplate.delete(email);
    }

<<<<<<< HEAD
=======
    // 이메일 인증번호 존재 여부
>>>>>>> yj_base
    public boolean hasKey(String email) {
        return stringRedisTemplate.hasKey(email);
    }

}
