package kr.co.teamA.Haru.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class HobbyDTO {
    private Long interestCategoryNum;
    private String mainCategory;
    private String userId;
}
