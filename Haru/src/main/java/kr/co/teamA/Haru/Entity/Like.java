package kr.co.teamA.Haru.Entity;

<<<<<<< HEAD
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

=======
>>>>>>> yj_base
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

<<<<<<< HEAD
    @ManyToOne
    @JoinColumn(name = "feedNum")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Feed feedNum;

    @ManyToOne
    @JoinColumn(name = "feedLikeBy")
    private Member feedLikeBy;
=======
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "feedNum")
    private Feed feed;

    @Column(length = 255, nullable = false)
    private String feedLikeBy;
>>>>>>> yj_base
}
