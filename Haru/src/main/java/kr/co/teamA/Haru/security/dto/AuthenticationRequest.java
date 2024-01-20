package kr.co.teamA.Haru.security.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AuthenticationRequest {
    // 사용자 이름을 저장
    private String id;
    // 사용자 비밀전호를 저장
    private String pwd;

    private String nickname;
}
