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
@Table(name = "QNA")
public class QnA {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QNA_SEQ")
    @SequenceGenerator(name = "QNA_SEQ", sequenceName = "QNA_SEQ", allocationSize = 1)
    private Long QNANUM;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private Member member;

    @Column(length = 100, nullable = false)
    private String qnaCategory;

    @Column(length = 255, nullable = false)
    private String qnaTitle;

    @Column(length = 500, nullable = false)
    private String qnaContent;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private String qnaState;

    @Column(nullable = false)
    private Date qnaCdate;

}
