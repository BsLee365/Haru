package kr.co.teamA.Haru.Entity;

<<<<<<< HEAD
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name = "FEEDIMG")
public class FeedImg {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FEEDIMG_SEQ_")
    @SequenceGenerator(name = "FEEDIMG_SEQ_", sequenceName = "FEEDIMG_SEQ_", allocationSize = 1)
    private Long feedImgNum;

<<<<<<< HEAD
    @OneToOne
    @JoinColumn(name = "feedNum")
    @OnDelete(action = OnDeleteAction.CASCADE)
=======
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "feedNum")
>>>>>>> yj_base
    private Feed feedNum;

    @Column(length = 255, nullable = false)
    private String feedImg;
}
