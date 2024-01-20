package kr.co.teamA.Haru.DTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RecommendItemsDTO {
    private List<ShowMyRecommendPlaceDTO> rec_list;
    private List<DiaryDTO> diary_list;
}
