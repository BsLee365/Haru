package kr.co.teamA.Haru.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ResetPwdDTO {
    private String id;
    private String pwd;
}
