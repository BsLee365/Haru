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
@Table(name = "FEEDLIKE")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LIKE_SEQ_")
    @SequenceGenerator(name = "LIKE_SEQ_", sequenceName = "LIKE_SEQ_", allocationSize = 1)
    private Long likeNum;

    @ManyToOne
    @JoinColumn(name = "feedNum")
    private Feed feedNum;

    @ManyToOne
    @JoinColumn(name = "feedLikeBy")
    private Member feedLikeBy;
}
