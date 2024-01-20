package kr.co.teamA.Haru.DTO;

import lombok.*;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class GetRecommendList {
    private String userid;
    private String startdate; // 받는건 String으로 받고 Service에서 변경
    private String enddate;
}
