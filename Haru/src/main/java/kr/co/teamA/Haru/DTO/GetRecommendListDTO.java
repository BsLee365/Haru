package kr.co.teamA.Haru.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class GetRecommendListDTO {
    private String userid;
    private String startdate; // 받는건 String으로 받고 Service에서 변경
    private String enddate;
}
