package kr.co.teamA.Haru.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(nullable = false, name = "userId", referencedColumnName = "userId")
    private Member member;

    @Column(length = 100, nullable = false)
    private String diaryTitle;

    @Column(length = 1200, nullable = false)
    private String diaryContext;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date diaryCdate;
}
