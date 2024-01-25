package kr.co.teamA.Haru.Repository.feed;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.teamA.Haru.Entity.Like;

@Repository
public interface FeedLikeRepository extends JpaRepository<Like, Long> {

    Integer countByFeedNum_FeedNum(Long feedNum);

    int countByFeedNum_FeedNumAndFeedLikeBy_UserId(Long feedNum, String userId);

    void deleteByFeedNum_FeedNumAndFeedLikeBy_UserId(Long feedNum, String userId);

    // int countByFeedNum_FeedNum(Long feedNum);

    int countByLikeNumIsNotNull();

    List<Like> findByFeedLikeBy_UserId(String userId);

}
