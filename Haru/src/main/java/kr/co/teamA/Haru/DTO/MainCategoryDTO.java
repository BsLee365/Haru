package kr.co.teamA.Haru.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
// 메인 카테고리를 위한 DTO
public class MainCategoryDTO {
    private String mainCategory;
    private Long placeSeasonInformation;
    private Long placeStressGrade;
}
