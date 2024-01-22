package kr.co.teamA.Haru.security.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AuthenticationRequest {
    private String id;
    private String pwd;
    private String nickname;
}
