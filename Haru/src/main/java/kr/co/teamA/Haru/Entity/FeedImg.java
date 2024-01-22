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
@Table(name = "FEEDIMG")
public class FeedImg {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FEEDIMG_SEQ_")
    @SequenceGenerator(name = "FEEDIMG_SEQ_", sequenceName = "FEEDIMG_SEQ_", allocationSize = 1)
    private Long feedImgNum;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "feedNum")
    private Feed feed;

    @Column(length = 255, nullable = false)
    private String feedImg;
}
