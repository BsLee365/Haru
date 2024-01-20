package kr.co.teamA.Haru.Repository.feed;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kr.co.teamA.Haru.Entity.FeedComment;

@Repository
public interface FeedCommentRepository extends JpaRepository<FeedComment, Integer> {

    List<FeedComment> findByFeedNum_FeedNum(Long feedNum);
}
