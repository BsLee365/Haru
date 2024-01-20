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
@Table(name = "MEMBER")
public class Member {

    @Id
    @Column(name = "userId", length = 100, nullable = false, unique = true)
    private String userId;

    @Column(length = 255, nullable = false)
    private String pwd;

    @Column(length = 50, nullable = false)
    private String nickname;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 255)
    private String profileImg;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private String cDate;

    @Column(length = 10)
    private String role;

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
