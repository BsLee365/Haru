package kr.co.teamA.Haru.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
// 관심사를 위한 DTO
public class HobbyDTO {
    private Long interestCategoryNum;
    private String mainCategory;
    private String userId;
}
