package kr.co.teamA.Haru.Repository.member;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.teamA.Haru.Entity.Alarm;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    List<Alarm> findAllByMember_UserId(String userId);

    int countByMember_UserIdAndFeedComment_CommentNumIsNotNull(String userId);

    int countByMember_UserIdAndLike_LikeNumIsNotNull(String userId);
}