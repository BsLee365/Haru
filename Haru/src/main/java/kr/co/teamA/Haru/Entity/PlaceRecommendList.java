package kr.co.teamA.Haru.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

<<<<<<< HEAD
import java.util.Date;

=======
>>>>>>> yj_base
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

<<<<<<< HEAD
    @ManyToOne
    @JoinColumn(name = "placeNumber")
    private Place place;

    @ManyToOne
=======
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "placeNumber")
    private Place place;

    @ManyToOne(cascade = CascadeType.REMOVE)
>>>>>>> yj_base
    @JoinColumn(name = "userId")
    private Member member;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
<<<<<<< HEAD
    private Date placeCdate;
=======
    private String placeCdate;
>>>>>>> yj_base

}