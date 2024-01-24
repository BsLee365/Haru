package kr.co.teamA.Haru.Entity;

<<<<<<< HEAD
import java.util.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
=======
>>>>>>> yj_base

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

<<<<<<< HEAD
=======

>>>>>>> yj_base
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
<<<<<<< HEAD
    private Long alarmNum;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "qnaNum", referencedColumnName = "qnaNum")
    private QnA qna;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private Member member;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "likeNum", referencedColumnName = "likeNum")
    private Like like;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "commentNum", referencedColumnName = "commentNum")
    private FeedComment feedComment;

    @Column(nullable = false)
    private Long alarmTypes;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false)
    private Date alarmCdate;
=======
    private Long alarmType;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "qnaNum", referencedColumnName = "qnaNum")
    private QnA qna;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private Member member;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "commentNum", referencedColumnName = "commentNum")
    private FeedComment feedComment;


    @Column(nullable = false)
    private Long alarmTypes;

>>>>>>> yj_base
}
