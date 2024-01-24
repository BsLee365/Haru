package kr.co.teamA.Haru.Entity;

import java.util.Date;

import jakarta.persistence.*;
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

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "feedNum")
    private Feed feed;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "userId")
    private Member member;

    @Column(length = 400, nullable = false)
    private String feedCommentContent;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date feedCdate;
}
