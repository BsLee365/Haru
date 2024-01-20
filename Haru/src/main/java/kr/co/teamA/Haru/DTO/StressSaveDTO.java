package kr.co.teamA.Haru.DTO;

import lombok.*;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class StressSaveDTO {

    // 일기 점수
    private Double diaryfigure;

    // 얼굴 점수
    private Double facefigure;

    // 척도 데이터
    //private double measureFigure;

    // 스트레스 총합
    private Double stressscore;

    // 스트레스 측정일자
    private Date stresscdate;

    // 유저 아이디
    private String userid;

}
