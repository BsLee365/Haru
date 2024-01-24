package kr.co.teamA.Haru.Entity;

import java.util.Date;

<<<<<<< HEAD
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
=======
import jakarta.persistence.*;
>>>>>>> yj_base
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

<<<<<<< HEAD
    @ManyToOne
    @JoinColumn(name = "feedNum")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Feed feedNum;

    @ManyToOne
    @JoinColumn(name = "userId")
    private Member userId;
=======
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "feedNum")
    private Feed feed;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "userId")
    private Member member;
>>>>>>> yj_base

    @Column(length = 400, nullable = false)
    private String feedCommentContent;

<<<<<<< HEAD
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false)
=======
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
>>>>>>> yj_base
    private Date feedCdate;
}
