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
@Table(name = "ALARM")
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ALARM_SEQ")
    @SequenceGenerator(name = "ALARM_SEQ", sequenceName = "ALARM_SEQ", allocationSize = 1)
    private Long alarmType;

    @OneToOne
    @JoinColumn(name = "qnaNum", referencedColumnName = "qnaNum")
    private QnA qna;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "commentNum", referencedColumnName = "commentNum")
    private FeedComment feedComment;


    @Column(nullable = false)
    private Long alarmTypes;

}
