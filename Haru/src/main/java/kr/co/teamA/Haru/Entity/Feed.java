package kr.co.teamA.Haru.Entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "FEED")
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FEED_SEQ_")
    @SequenceGenerator(name = "FEED_SEQ_", sequenceName = "FEED_SEQ_", allocationSize = 1)
    private Long feedNum;

    @ManyToOne
    @JoinColumn(name = "userId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "placeNum")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Place placeNum;

    @Column(length = 500, nullable = false)
    private String feedContent;

    @Column(length = 255, nullable = false)
    private String feedCategory;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false)
    private String feedCdate;

    // @Column(length = 255)
    // private String feedHashTag;
}
