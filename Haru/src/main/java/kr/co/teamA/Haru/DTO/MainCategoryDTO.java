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
public class MainCategoryDTO {
    private String mainCategory;
    private Long placeSeasonInformation;
    private Long placeStressGrade;
}
