package kr.co.teamA.Haru.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// 유저 아이디 중복체크를 위한 DTO
public class UserIdDTO {
    private String userId;
}
