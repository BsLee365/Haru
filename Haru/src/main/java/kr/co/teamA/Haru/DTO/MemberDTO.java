package kr.co.teamA.Haru.DTO;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class MemberDTO {
    private String userId;
    private String id; // 0121 머지
    private String pwd;
    private String nickname;
    private String name;
    private String email;
    private String profileImg;
    private String cDate;
}
