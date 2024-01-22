package kr.co.teamA.Haru.Entity;

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
@Table(name = "FEEDHASHTAG")
public class FeedHashTag {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FEEDHASHTAG_SEQ_")
    @SequenceGenerator(name = "FEEDHASHTAG_SEQ_", sequenceName = "FEEDHASHTAG_SEQ_", allocationSize = 1)
    private long feedHashTagNum;

    private String feedHashTag;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "feedNum")
    private Feed feed_num;
}
