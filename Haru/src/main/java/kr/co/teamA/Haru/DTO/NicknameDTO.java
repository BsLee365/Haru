package kr.co.teamA.Haru.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
// 닉네임 중복체크를 위한 DTO
public class NicknameDTO {
    private String nickname;
}
