package kr.co.teamA.Haru.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name = "FEEDIMG")
public class FeedImg {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FEEDIMG_SEQ_")
    @SequenceGenerator(name = "FEEDIMG_SEQ_", sequenceName = "FEEDIMG_SEQ_", allocationSize = 1)
    private Long feedImgNum;

    @OneToOne
    @JoinColumn(name = "feedNum")
    private Feed feed;

    @Column(length = 255, nullable = false)
    private String feedImg;
}
