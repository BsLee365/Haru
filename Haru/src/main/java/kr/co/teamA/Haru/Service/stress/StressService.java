package kr.co.teamA.Haru.Service.stress;

import kr.co.teamA.Haru.security.filter.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StressService {

    JwtTokenProvider jwtTokenProvider;
    public String tokenValidation(String token) {
        boolean verification = jwtTokenProvider.validateToken(token);
        if (verification) {
            return "success";
        } else {
            return "fail";
        }
    }
}
