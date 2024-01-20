package kr.co.teamA.Haru.Repository.feed;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.teamA.Haru.Entity.FeedHashTag;

@Repository
public interface FeedHashTagRepository extends JpaRepository<FeedHashTag, Integer> {

    List<FeedHashTag> findByFeedNum_FeedNum(Long feedNum);

}
