package kr.co.teamA.Haru.DTO;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DiaryDTO {
    private Long diaryNum;

    private String userId;

    private String diaryTitle;

    private String diaryContext;

    private Date diaryCdate;
}