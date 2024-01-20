package kr.co.teamA.Haru.Repository;

import kr.co.teamA.Haru.DTO.MemberDTO;
import kr.co.teamA.Haru.DTO.UserInfoDTO;
import kr.co.teamA.Haru.Entity.Member;
import org.hibernate.sql.Update;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
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


    Optional<UserDetails> findByUserId(String userId);

    MemberDTO findMemberByUserId(String userId);
    Member findMemberByuserId(String memberId);

    @Query("SELECT m.userId FROM Member m WHERE m.email = :email")
    String findByMemberId(String email);

    @Query("SELECT new kr.co.teamA.Haru.DTO.UserInfoDTO(u.id, u.nickname, u.email, u.name) FROM Member u WHERE u.userId = :userId")
    UserInfoDTO findByUserId2(@Param("userId") String userId);


    Optional findUserIdByEmailAndName(String email, String name);

    @Modifying
    @Query("UPDATE Member u SET u.pwd = :pwd WHERE u.userId = :id")
    void updateByPassword(@Param("id") String userId, @Param("pwd") String password);

    @Query("select m from Member m where m.userId = :userId")
    String checkPassword(@Param("userId") String userId);

    @Modifying
    @Query("UPDATE Member u SET u.nickname= :nickname, u.email= :email, u.name= :name WHERE u.userId = :id")
    void updateByMember(@Param("id") String userId, @Param("nickname") String nickname, @Param("email") String email, @Param("name") String name);

}
