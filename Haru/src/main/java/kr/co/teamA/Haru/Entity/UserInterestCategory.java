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
@Table(name = "USERINTERESTCATEGORY")
public class UserInterestCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERINTERESTCATEGORY_SEQ_")
    @SequenceGenerator(name = "USERINTERESTCATEGORY_SEQ_", sequenceName = "USERINTERESTCATEGORY_SEQ_", allocationSize = 1)
    private Long interestCategoryNum;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "userId")
    private Member userId;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "mainCategory")
    private MainCategory mainCategory;
}
