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
@Table(name = "ANSWER")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ANSWER_SEQ_SEQ")
    @SequenceGenerator(name = "ANSWER_SEQ", sequenceName = "ANSWER_SEQ_SEQ", allocationSize = 1)
    private Long answerNum;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "qnaNum", referencedColumnName = "qnaNum")
    private QnA qnaNum;

    @Column(length = 500, nullable = false)
    private String answerContent;

    @Column(nullable = false)
    private Date answerCdate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "adminId", referencedColumnName = "adminId")
    private ADMIN adminId;

    @Column(length = 500, nullable = false)
    private String qnaContent;

}
