package kr.co.teamA.Haru.Repository.feed;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.teamA.Haru.Entity.Like;

@Repository
public interface FeedLikeRepository extends JpaRepository<Like, Long> {

    Integer countByFeedNum_FeedNum(Long feedNum);

    int countByFeedNum_FeedNumAndFeedLikeBy_UserId(int feedNum, String userId);

    void deleteByFeedNum_FeedNumAndFeedLikeBy_UserId(int feedNum, String userId);

}
