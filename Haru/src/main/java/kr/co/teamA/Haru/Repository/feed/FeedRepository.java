package kr.co.teamA.Haru.Repository.feed;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.teamA.Haru.Entity.Feed;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {

    List<Feed> findAll();

    Feed findByFeedNum(int feedNum);

}
