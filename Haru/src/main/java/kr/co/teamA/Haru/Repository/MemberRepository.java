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
<<<<<<< HEAD
    @Query("SELECT new kr.co.teamA.Haru.DTO.UserIdDTO(m.userId) FROM Member m WHERE m.userId = :userId")
    Optional findUserIdById(@Param("userId") String userId);

    @Query("SELECT COUNT(m) FROM Member m WHERE m.email = :email")
    int findUserEmailByEmail(@Param("email") String email);

    @Query("SELECT new kr.co.teamA.Haru.DTO.NicknameDTO(m.nickname) FROM Member m WHERE m.nickname = :nickname")
    Optional findNicknameByNickname(@Param("nickname") String nickname);

//    Optional<UserDetails> findByUserId(String userId);

    MemberDTO findMemberByUserId(String userId);
    Member findMemberByuserId(String memberId);

    // 추가함. - 이범석
    //Optional<Member> findById(@Param("userId") String userId);

    // 추가함. - 이범석
    Optional<Member> findByUserId(@Param("userId") String userId);

    @Query("SELECT m.userId FROM Member m WHERE m.email = :email")
    String findByMemberId(String email);

    @Query("SELECT new kr.co.teamA.Haru.DTO.UserInfoDTO(u.id, u.nickname, u.email, u.name) FROM Member u WHERE u.userId = :userId")
    UserInfoDTO findByUserId2(@Param("userId") String userId);

    Optional findUserIdByEmailAndName(String email, String name);

=======
    // 회원가입시 아이디 중복체크
    @Query("SELECT new kr.co.teamA.Haru.DTO.UserIdDTO(m.userId) FROM Member m WHERE m.userId = :userId")
    Optional findUserIdById(@Param("userId") String userId);
    // 회원가입시 이메일 중복체크
    @Query("SELECT COUNT(m) FROM Member m WHERE m.email = :email")
    int findUserEmailByEmail(@Param("email") String email);
    // 회원가입시 닉네임 중복체크
    @Query("SELECT new kr.co.teamA.Haru.DTO.NicknameDTO(m.nickname) FROM Member m WHERE m.nickname = :nickname")
    Optional findNicknameByNickname(@Param("nickname") String nickname);

    // 아이디로 회원정보 찾기
    Optional<UserDetails> findByUserId(String userId);


    Member findMemberByuserId(String memberId);

    // 이메일로 회원 아이디 찾기
    @Query("SELECT m.userId FROM Member m WHERE m.email = :email")
    String findByMemberId(String email);

    // 회원 아이디를 통해서 아이디, 닉네임, 이메일, 이름 가져오기
    @Query("SELECT new kr.co.teamA.Haru.DTO.UserInfoDTO(u.id, u.nickname, u.email, u.name) FROM Member u WHERE u.userId = :userId")
    UserInfoDTO findByUserId2(@Param("userId") String userId);

    // 이메일과 이름으로 회원 아이디 찾기
    Optional findUserIdByEmailAndName(String email, String name);

    // 이메일과 이름과 아이디로 회원 아이디 찾기
    Optional findUserIdByEmailAndNameAndUserId(String email, String name, String userId);

    // 비밀번호 수정
>>>>>>> yj_base
    @Modifying
    @Query("UPDATE Member u SET u.pwd = :pwd WHERE u.userId = :id")
    void updateByPassword(@Param("id") String userId, @Param("pwd") String password);

<<<<<<< HEAD
    @Query("select m from Member m where m.userId = :userId")
    String checkPassword(@Param("userId") String userId);

=======
    // 비밀번호 확인
    @Query("select m from Member m where m.userId = :userId")
    String checkPassword(@Param("userId") String userId);

    // 회원정보 수정
>>>>>>> yj_base
    @Modifying
    @Query("UPDATE Member u SET u.nickname= :nickname, u.email= :email, u.name= :name WHERE u.userId = :id")
    void updateByMember(@Param("id") String userId, @Param("nickname") String nickname, @Param("email") String email, @Param("name") String name);

<<<<<<< HEAD
    int countByUserIdIsNotNull();
=======
>>>>>>> yj_base
}
