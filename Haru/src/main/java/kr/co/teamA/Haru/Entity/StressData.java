package kr.co.teamA.Haru.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

<<<<<<< HEAD
import java.sql.Date;
=======
import java.util.Date;

>>>>>>> BeomSeok

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "STRESSDATA")
public class StressData {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRESS_DATA_SEQ_")
    @SequenceGenerator(name = "STRESS_DATA_SEQ_", sequenceName = "STRESS_DATA_SEQ_", allocationSize = 1)
    private Long STRESSDATANUM;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private Member member;

    @Column(nullable = false)
<<<<<<< HEAD
    private Long faceData;

    @Column(nullable = false)
    private Long diaryData;

    @Column(nullable = false)
    private Long measureData;

    @Column(nullable = false)
    private String stressScore;
=======
    private Double faceData;

    @Column(nullable = false)
    private Double diaryData;

      // 척도 안받아옴.
//    @Column(nullable = false)
//    private Long measureData;

    @Column(nullable = false)
    private Double stressScore;
>>>>>>> BeomSeok

    @Column(nullable = false)
    private Date stressCdate;

}
