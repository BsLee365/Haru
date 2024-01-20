package kr.co.teamA.Haru.Entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
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
    private Member member;

    @ManyToOne
    @JoinColumn(name = "placeNum")
    private Place place;

    @Column(length = 500, nullable = false)
    private String feedContent;

    @Column(length = 255, nullable = false)
    private String feedCategory;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private String feedCdate;

    @Column(length = 255)
    private String feedHashTag;
}
