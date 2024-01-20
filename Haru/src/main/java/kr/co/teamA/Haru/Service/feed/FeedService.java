package kr.co.teamA.Haru.Service.feed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import kr.co.teamA.Haru.Entity.Feed;
import kr.co.teamA.Haru.Entity.FeedComment;
import kr.co.teamA.Haru.Entity.FeedHashTag;
import kr.co.teamA.Haru.Entity.FeedImg;
import kr.co.teamA.Haru.Entity.Like;
import kr.co.teamA.Haru.Entity.Member;
import kr.co.teamA.Haru.Repository.MemberRepository;
import kr.co.teamA.Haru.Repository.feed.FeedCommentRepository;
import kr.co.teamA.Haru.Repository.feed.FeedHashTagRepository;
import kr.co.teamA.Haru.Repository.feed.FeedImgRepository;
import kr.co.teamA.Haru.Repository.feed.FeedLikeRepository;
import kr.co.teamA.Haru.Repository.feed.FeedRepository;

@Service
public class FeedService {

    public final FeedRepository feedRepository;
    public final FeedCommentRepository feedCommentRepository;
    public final FeedHashTagRepository feedHashTagRepository;
    public final FeedImgRepository feedImgRepository;
    public final FeedLikeRepository feedLikeRepository;
    public final MemberRepository memberRepository;

    public FeedService(FeedRepository feedRepository, FeedCommentRepository feedCommentRepository,
            FeedHashTagRepository feedHashTagRepository, FeedImgRepository feedImgRepository,
            FeedLikeRepository feedLikeRepository, MemberRepository memberRepository) {
        this.feedRepository = feedRepository;
        this.feedCommentRepository = feedCommentRepository;
        this.feedHashTagRepository = feedHashTagRepository;
        this.feedImgRepository = feedImgRepository;
        this.feedLikeRepository = feedLikeRepository;
        this.memberRepository = memberRepository;
    }

    public Map<String, Object> getFeedList() {
        List<Feed> feed = feedRepository.findAll();
        List<FeedComment> feedComment = new ArrayList<>();
        List<FeedHashTag> feedHashTag = new ArrayList<>();
        List<FeedImg> feedImg = new ArrayList<>();
        List<Integer> feedLike = new ArrayList<>();
        for (Feed f : feed) {
            feedComment.addAll(feedCommentRepository.findByFeedNum_FeedNum(f.getFeedNum()));
            feedHashTag.addAll(feedHashTagRepository.findByFeedNum_FeedNum(f.getFeedNum()));
            feedImg.addAll(feedImgRepository.findByFeedNum_FeedNum(f.getFeedNum()));
            feedLike.add(feedLikeRepository.countByFeedNum_FeedNum(f.getFeedNum()));
        }
        Map<String, Object> feedList = new HashMap<String, Object>();

        feedList.put("feed", feed);
        feedList.put("feedComment", feedComment);
        feedList.put("feedHashTag", feedHashTag);
        feedList.put("feedImg", feedImg);
        feedList.put("feedLike", feedLike);

        return feedList;
    }

    public List<FeedComment> addFeedComment(int feedNum, String userId, String feedCommentContent) {
        Feed feed = feedRepository.findByFeedNum(feedNum);
        Member member = memberRepository.findMemberByuserId(userId);
        FeedComment feedComment = FeedComment.builder()
                .feedNum(feed)
                .userId(member)
                .feedCommentContent(feedCommentContent)
                .build();
        feedCommentRepository.save(feedComment);

        List<FeedComment> feedCommentList = feedCommentRepository.findByFeedNum_FeedNum((long) feedNum);

        return feedCommentList;
    }

    public int modifyFeedLike(int feedNum, String userId) {
        Feed feed = feedRepository.findByFeedNum(feedNum);
        Member member = memberRepository.findMemberByuserId(userId);
        if (feedLikeRepository.countByFeedNum_FeedNumAndFeedLikeBy_UserId(feedNum, userId) == 0) {
            Like like = Like.builder()
                    .feedNum(feed)
                    .feedLikeBy(member)
                    .build();
            feedLikeRepository.save(like);
        } else {
            feedLikeRepository.deleteByFeedNum_FeedNumAndFeedLikeBy_UserId(feedNum, userId);
        }

        int likes = feedLikeRepository.countByFeedNum_FeedNumAndFeedLikeBy_UserId(feedNum, userId);
        return likes;
    }

}
