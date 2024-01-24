package kr.co.teamA.Haru.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
// 마이페이지를 위한 DTO
public class MyPageDTO {
    private String userId;
    private String name;
    private String nickName;
}
