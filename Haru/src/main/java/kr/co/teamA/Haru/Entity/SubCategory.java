package kr.co.teamA.Haru.Entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
@Table(name = "SUBCATEGORY")
public class SubCategory {

    @Id
    @Column(length = 255, nullable = false)
    private String subCategory;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "mainCategory", referencedColumnName = "mainCategory")
    private MainCategory mainCategory;

    private Long placeIsOutdoor;
}
