package kr.co.teamA.Haru.Entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.*;

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

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "feedNum")
    private Feed feedNum;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "userId")
    private Member userId;

    @Column(length = 400, nullable = false)
    private String feedCommentContent;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false)
    private Date feedCdate;
}
