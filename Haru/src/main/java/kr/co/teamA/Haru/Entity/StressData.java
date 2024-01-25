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
@Table(name = "STRESSDATA")
public class StressData {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRESS_DATA_SEQ_")
    @SequenceGenerator(name = "STRESS_DATA_SEQ_", sequenceName = "STRESS_DATA_SEQ_", allocationSize = 1)
    private Long STRESSDATANUM;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private Member member;

    @Column(nullable = false)
    private Double faceData;

    @Column(nullable = false)
    private Double diaryData;

    @Column(nullable = false)
    private Double stressScore;

    @Column(nullable = false)
    private Date stressCdate;

}
