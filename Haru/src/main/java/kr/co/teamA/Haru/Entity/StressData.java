package kr.co.teamA.Haru.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

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
    private Long faceData;

    @Column(nullable = false)
    private Long diaryData;

    @Column(nullable = false)
    private Long measureData;

    @Column(nullable = false)
    private String stressScore;

    @Column(nullable = false)
    private Date stressCdate;

}
