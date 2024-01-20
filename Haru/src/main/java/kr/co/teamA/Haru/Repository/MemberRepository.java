package kr.co.teamA.Haru.Repository;

import kr.co.teamA.Haru.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    @Query("SELECT new kr.co.teamA.Haru.DTO.UserIdDTO(m.userId) FROM Member m WHERE m.userId = :userId")
    Optional findUserIdById(@Param("userId") String userId);

    @Query("SELECT COUNT(m) FROM Member m WHERE m.email = :email")
    int findUserEmailByEmail(@Param("email") String email);

    @Query("SELECT new kr.co.teamA.Haru.DTO.NicknameDTO(m.nickname) FROM Member m WHERE m.nickname = :nickname")
    Optional findNicknameByNickname(@Param("nickname") String nickname);

    // 추가함. - 이범석
    //Optional<Member> findById(@Param("userId") String userId);

    // 추가함. - 이범석
    Optional<Member> findByUserId(@Param("userId") String userId);
}