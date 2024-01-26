package kr.co.teamA.Haru.Service.home;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import kr.co.teamA.Haru.Entity.Member;
import kr.co.teamA.Haru.Entity.StressData;
import kr.co.teamA.Haru.Repository.MemberRepository;
import kr.co.teamA.Haru.Repository.StressRepository;
import kr.co.teamA.Haru.Repository.feed.FeedLikeRepository;
import kr.co.teamA.Haru.Repository.feed.FeedRepository;

@Service
public class HomeService {

    public final MemberRepository memberRepository;
    public final FeedRepository feedRepository;
    public final FeedLikeRepository feedLikeRepository;
    public final StressRepository stressRepository;

    public HomeService(MemberRepository memberRepository, FeedRepository feedRepository,
            FeedLikeRepository feedLikeRepository, StressRepository stressRepository) {
        this.memberRepository = memberRepository;
        this.feedRepository = feedRepository;
        this.feedLikeRepository = feedLikeRepository;
        this.stressRepository = stressRepository;
    }

    public Map<String, Object> getHomeData() {

        Map<String, Object> homeData = new HashMap<>();
        int memberCount = memberRepository.countByUserIdIsNotNull();
        int feedCount = feedRepository.countByFeedNumIsNotNull();
        int likeCount = feedLikeRepository.countByLikeNumIsNotNull();
        List<StressData> stressList = stressRepository.findAll();
        homeData.put("memberCount", memberCount);
        homeData.put("feedCount", feedCount);
        homeData.put("likeCount", likeCount);
        homeData.put("stressList", stressList);

        return homeData;
    }

}
