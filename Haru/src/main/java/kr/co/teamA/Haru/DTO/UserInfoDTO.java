package kr.co.teamA.Haru.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// 유저 정보를 위한 DTO
public class UserInfoDTO {
    private String id;
    private String nickname;
    private String email;
    private String name;
}
