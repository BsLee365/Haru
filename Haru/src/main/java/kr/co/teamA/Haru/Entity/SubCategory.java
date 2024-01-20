package kr.co.teamA.Haru.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "SUBCATEGORY")
public class SubCategory {

    @Id
    @Column(length = 255, nullable = false)
    private String subCategory;

    @ManyToOne
    @JoinColumn(name = "mainCategory", referencedColumnName = "mainCategory")
    private MainCategory mainCategory;

    // placeIsOutDoor => placeIsOutdoor
    private Long placeIsOutdoor;
}
