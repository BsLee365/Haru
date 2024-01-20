package kr.co.teamA.Haru.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

<<<<<<< HEAD
=======
import java.util.List;

>>>>>>> BeomSeok
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

<<<<<<< HEAD
    private Long placeStressGrade;

    private Long placeSeasonInformation;
=======
    @Column(nullable = false)
    private Long placeStressGrade;

    @Column(nullable = false)
    private Long placeSeasonInformation;



>>>>>>> BeomSeok
}
