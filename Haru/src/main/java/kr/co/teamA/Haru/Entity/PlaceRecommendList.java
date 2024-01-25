package kr.co.teamA.Haru.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "PLACERECOMMENDLIST")
public class PlaceRecommendList {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PLACERECOMMENDLIST_SEQ_")
    @SequenceGenerator(name = "PLACERECOMMENDLIST_SEQ_", sequenceName = "PLACERECOMMENDLIST_SEQ_", allocationSize = 1)
    private Long recommendNum;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "placeNumber")
    private Place place;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "userId")
    private Member member;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date placeCdate;

}