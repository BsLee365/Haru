package kr.co.teamA.Haru.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
// 비밀번호 재설정을 위한 DTO
public class FindUserPwdDTO {
    private String userId;
    private String userName;
    private String email;
}
