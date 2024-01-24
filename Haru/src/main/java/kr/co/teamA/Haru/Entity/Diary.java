package kr.co.teamA.Haru.Entity;

import jakarta.persistence.*;
<<<<<<< HEAD
import lombok.*;

import java.util.Date;

=======
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
>>>>>>> yj_base

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "DIARY")
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Diary_SEQ")
    @SequenceGenerator(name = "Diary_SEQ", sequenceName = "Diary_SEQ", allocationSize = 1)
    private Long diaryNum;

<<<<<<< HEAD
    @ManyToOne
    @JoinColumn(nullable = false, name = "userId", referencedColumnName = "userId")
=======
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
>>>>>>> yj_base
    private Member member;

    @Column(length = 100, nullable = false)
    private String diaryTitle;

    @Column(length = 1200, nullable = false)
    private String diaryContext;

<<<<<<< HEAD
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date diaryCdate;
=======
    @Column(nullable = false)
    private Date diaryCdate;

>>>>>>> yj_base
}
