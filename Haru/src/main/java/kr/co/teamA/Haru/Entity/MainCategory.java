package kr.co.teamA.Haru.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "MAINCATEGORY")
public class MainCategory {
    @Id
    @Column(length = 255, nullable = false)
    private String mainCategory;

    private Long placeStressGrade;

    private Long placeSeasonInformation;
}
