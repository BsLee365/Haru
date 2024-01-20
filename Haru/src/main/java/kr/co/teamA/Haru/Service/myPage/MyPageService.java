package kr.co.teamA.Haru.Service.myPage;

import kr.co.teamA.Haru.security.filter.JwtTokenProvider;

public class MyPageService {

    JwtTokenProvider jwtTokenProvider;
    public String myPageData(String token) {
        if (jwtTokenProvider.validateToken(token)) {
            return "false";
        }
//        String data = jwtTokenProvider.getUsername(token);
//        System.out.println(data);
        return "data";

    }
}
