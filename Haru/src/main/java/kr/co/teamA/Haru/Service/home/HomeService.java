package kr.co.teamA.Haru.Service.home;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import kr.co.teamA.Haru.Entity.Member;
import kr.co.teamA.Haru.Repository.MemberRepository;
import kr.co.teamA.Haru.Repository.feed.FeedLikeRepository;
import kr.co.teamA.Haru.Repository.feed.FeedRepository;

@Service
public class HomeService {

    public final MemberRepository memberRepository;
    public final FeedRepository feedRepository;
    public final FeedLikeRepository feedLikeRepository;

    public HomeService(MemberRepository memberRepository, FeedRepository feedRepository,
            FeedLikeRepository feedLikeRepository) {
        this.memberRepository = memberRepository;
        this.feedRepository = feedRepository;
        this.feedLikeRepository = feedLikeRepository;
    }

    public Map<String, Object> getHomeData() {

        Map<String, Object> homeData = new HashMap<>();
        int memberCount = memberRepository.countByUserIdIsNotNull();
        int feedCount = feedRepository.countByFeedNumIsNotNull();
        int likeCount = feedLikeRepository.countByLikeNumIsNotNull();

        homeData.put("memberCount", memberCount);
        homeData.put("feedCount", feedCount);
        homeData.put("likeCount", likeCount);

        return homeData;
    }

}
