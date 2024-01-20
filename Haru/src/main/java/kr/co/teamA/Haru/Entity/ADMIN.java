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
@Table(name = "ADMIN")
public class ADMIN {

    @Id
    @Column(length = 100, nullable = false)
    private String adminId;

    @Column(length = 255, nullable = false)
    private String adminPwd;

    @Column(length = 50, nullable = false)
    private String adminName;

    @Column(nullable = false)
    private Date adminCdate;

}
