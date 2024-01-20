package kr.co.teamA.Haru.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class FeedDTO {
    private Long feedNum;
    private String feedCategory;
    private String feedCdate;
    private String feedContent;
    private String userId;
    private Long placeNum;
}
