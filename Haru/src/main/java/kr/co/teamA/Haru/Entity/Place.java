package kr.co.teamA.Haru.Entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
@Table(name = "PLACE")
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PLACE_SEQ_")
    @SequenceGenerator(name = "PLACE_SEQ_", sequenceName = "PLACE_SEQ_", allocationSize = 1)
    private Long placeNum;

    @Column(length = 255, nullable = false)
    private String placeName;

    @Column(length = 255, nullable = false)
    private String placeAddress;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "subCategory")
    private SubCategory subCategory;

    @Column(length = 1000)
    private String placeImg;

    @Column(length = 500, nullable = false)
    private String placeLink;

    @Column(nullable = false)
    private Long placeScore;

    @Column(nullable = false)
    private Long placeActive;
}
