package kr.co.teamA.Haru.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
// 비밀번호 재설정을 위한 DTO
public class ResetPwdDTO {
    private String id;
    private String pwd;
}
