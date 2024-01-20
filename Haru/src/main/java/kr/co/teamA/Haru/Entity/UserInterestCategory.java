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
@Table(name = "USERINTERESTCATEGORY")
public class UserInterestCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERINTERESTCATEGORY_SEQ_")
    @SequenceGenerator(name = "USERINTERESTCATEGORY_SEQ_", sequenceName = "USERINTERESTCATEGORY_SEQ_", allocationSize = 1)
    private Long interestCategoryNum;

    @ManyToOne
    @JoinColumn(name = "userId") // 적절한 컬럼 이름으로 변경
    private Member members;

    @ManyToOne
    @JoinColumn(name = "mainCategory") // 적절한 컬럼 이름으로 변경
    private MainCategory mainCategoryList;
}
