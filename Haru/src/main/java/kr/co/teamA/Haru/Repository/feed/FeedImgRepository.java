package kr.co.teamA.Haru.Repository.feed;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.teamA.Haru.Entity.FeedImg;

public interface FeedImgRepository extends JpaRepository<FeedImg, Long> {
    
    List<FeedImg> findByFeedNum_FeedNum(Long feedNum);

}
