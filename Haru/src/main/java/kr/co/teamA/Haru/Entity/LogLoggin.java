package kr.co.teamA.Haru.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "LOGLOGGIN")
public class LogLoggin {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOGLOGGINNUM_SEQ_")
    @SequenceGenerator(name = "LOGLOGGINNUM_SEQ_", sequenceName = "LOGLOGGINNUM_SEQ_", allocationSize = 1)
    private Long logLogginNum;

    @Column(nullable = false)
    private Long accessInfo;

    @ManyToOne
    @JoinColumn(name = "adminId", referencedColumnName = "adminId")
    private ADMIN admin;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private Member member;

    @Column(nullable = false)
    private Date accessTime;

    @Column(length = 100, nullable = false)
    private String accessPlace;

    @Column(length = 255, nullable = false)
    private String accessWay;

}