package kr.co.teamA.Haru.Entity;

import java.util.Date;

<<<<<<< HEAD
=======
import org.springframework.format.annotation.DateTimeFormat;

>>>>>>> junghyoun
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
<<<<<<< HEAD
=======
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
>>>>>>> junghyoun
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "FEEDCOMMENT")
public class FeedComment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FEEDCOMMENT_SEQ_")
    @SequenceGenerator(name = "FEEDCOMMENT_SEQ_", sequenceName = "FEEDCOMMENT_SEQ_", allocationSize = 1)
    private int commentNum;

    @ManyToOne
    @JoinColumn(name = "feedNum")
<<<<<<< HEAD
    private Feed feed;

    @ManyToOne
    @JoinColumn(name = "userId")
    private Member member;
=======
    private Feed feedNum;

    @ManyToOne
    @JoinColumn(name = "userId")
    private Member userId;
>>>>>>> junghyoun

    @Column(length = 400, nullable = false)
    private String feedCommentContent;

<<<<<<< HEAD
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
=======
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false)
>>>>>>> junghyoun
    private Date feedCdate;
}
