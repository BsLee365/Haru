package kr.co.teamA.Haru.DTO;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
<<<<<<< HEAD
public class MemberDTO {
//    private String userId;
    private String id; // 0121 머지
=======
// 회원 정보를 위한 DTO
public class MemberDTO {
    private String id;
>>>>>>> yj_base
    private String pwd;
    private String nickname;
    private String name;
    private String email;
    private String profileImg;
    private String cDate;
}
