package kr.co.teamA.Haru.Service.feed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.teamA.Haru.Entity.Alarm;
import kr.co.teamA.Haru.Entity.Feed;
import kr.co.teamA.Haru.Entity.FeedComment;
import kr.co.teamA.Haru.Entity.FeedHashTag;
import kr.co.teamA.Haru.Entity.FeedImg;
import kr.co.teamA.Haru.Entity.Like;
import kr.co.teamA.Haru.Entity.Member;
import kr.co.teamA.Haru.Entity.Place;
import kr.co.teamA.Haru.Repository.MemberRepository;
import kr.co.teamA.Haru.Repository.feed.FeedCommentRepository;
import kr.co.teamA.Haru.Repository.feed.FeedHashTagRepository;
import kr.co.teamA.Haru.Repository.feed.FeedImgRepository;
import kr.co.teamA.Haru.Repository.feed.FeedLikeRepository;
import kr.co.teamA.Haru.Repository.feed.FeedRepository;
import kr.co.teamA.Haru.Repository.member.AlarmRepository;
import kr.co.teamA.Haru.Repository.place.PlaceRepository;

@Service
public class FeedService {

    public final FeedRepository feedRepository;
    public final FeedCommentRepository feedCommentRepository;
    public final FeedHashTagRepository feedHashTagRepository;
    public final FeedImgRepository feedImgRepository;
    public final FeedLikeRepository feedLikeRepository;
    public final MemberRepository memberRepository;
    public final PlaceRepository placeRepository;
    public final AlarmRepository alarmRepository;

    public FeedService(FeedRepository feedRepository, FeedCommentRepository feedCommentRepository,
                       FeedHashTagRepository feedHashTagRepository, FeedImgRepository feedImgRepository,
                       FeedLikeRepository feedLikeRepository, MemberRepository memberRepository, PlaceRepository placeRepository,
                       AlarmRepository alarmRepository) {
        this.feedRepository = feedRepository;
        this.feedCommentRepository = feedCommentRepository;
        this.feedHashTagRepository = feedHashTagRepository;
        this.feedImgRepository = feedImgRepository;
        this.feedLikeRepository = feedLikeRepository;
        this.memberRepository = memberRepository;
        this.placeRepository = placeRepository;
        this.alarmRepository = alarmRepository;
    }

    public Map<String, Object> getFeedList(String userId) {
        List<Feed> feed = feedRepository.findAll();
        List<FeedComment> feedComment = new ArrayList<>();
        List<FeedHashTag> feedHashTag = new ArrayList<>();
        List<FeedImg> feedImg = new ArrayList<>();
        List<Integer> feedLike = new ArrayList<>();
        List<Like> myFeedLikes = new ArrayList<>();
        for (Feed f : feed) {
            feedComment.addAll(feedCommentRepository.findByFeedNum_FeedNum(f.getFeedNum()));
            feedHashTag.addAll(feedHashTagRepository.findByFeedNum_FeedNum(f.getFeedNum()));
            feedImg.addAll(feedImgRepository.findByFeedNum_FeedNum(f.getFeedNum()));
            feedLike.add(feedLikeRepository.countByFeedNum_FeedNum(f.getFeedNum()));
        }
        myFeedLikes.addAll(feedLikeRepository.findByFeedLikeBy_UserId(userId));
        Map<String, Object> feedList = new HashMap<String, Object>();

        feedList.put("feed", feed);
        feedList.put("feedComment", feedComment);
        feedList.put("feedHashTag", feedHashTag);
        feedList.put("feedImg", feedImg);
        feedList.put("feedLike", feedLike);
        feedList.put("myFeedLikes", myFeedLikes);

        return feedList;
    }

    @Transactional
    public List<FeedComment> addFeedComment(int feedNum, String userId, String feedCommentContent, String feedUserId) {
        Feed feed = feedRepository.findByFeedNum(feedNum);
        Member member = memberRepository.findMemberByuserId(userId);
        Member feedUser = memberRepository.findMemberByuserId(feedUserId);
        FeedComment feedComment = FeedComment.builder()
                .feedNum(feed)
                .userId(member)
                .feedCommentContent(feedCommentContent)
                .build();
        feedCommentRepository.save(feedComment);

        Alarm alarm = Alarm.builder()
                .member(feedUser)
                .feedComment(feedComment)
                .alarmTypes((long) 2)
                .build();
        alarmRepository.save(alarm);

        List<FeedComment> feedCommentList = feedCommentRepository.findByFeedNum_FeedNum((long) feedNum);

        return feedCommentList;
    }

    @Transactional
    public int modifyFeedLike(int feedNum, String userId, String feedUserId) {
        Feed feed = feedRepository.findByFeedNum(feedNum);
        Member member = memberRepository.findMemberByuserId(userId);
        Member feedUser = memberRepository.findMemberByuserId(feedUserId);
        if (feedLikeRepository.countByFeedNum_FeedNumAndFeedLikeBy_UserId(feedNum, userId) == 0) {
            Like like = Like.builder()
                    .feedNum(feed)
                    .feedLikeBy(member)
                    .build();
            feedLikeRepository.save(like);

            Alarm alarm = Alarm.builder()
                    .member(feedUser)
                    .like(like)
                    .alarmTypes((long) 1)
                    .build();
            alarmRepository.save(alarm);
        } else {
            feedLikeRepository.deleteByFeedNum_FeedNumAndFeedLikeBy_UserId(feedNum, userId);
        }

        int likes = feedLikeRepository.countByFeedNum_FeedNum(feedNum);
        return likes;
    }

    @Transactional
    public void uploadFeed(Map<String, Object> data, List<String> feedImgs, List<String> hashTags) {
        Member member = memberRepository.findMemberByuserId((String) data.get("userId"));
        Place place = placeRepository.findByPlaceNum(Long.parseLong((String) data.get("placeNum")));
        System.out.println(data.get("hashTag"));

        Feed feed = Feed.builder()
                .member(member)
                .feedCategory(place.getSubCategory().getMainCategory().getMainCategory())
                .placeNum(place)
                .feedContent((String) data.get("contents"))
                .build();
        feedRepository.save(feed);
        for (String feedImg : feedImgs) {
            FeedImg img = FeedImg.builder()
                    .feedNum(feed)
                    .feedImg(feedImg)
                    .build();
            feedImgRepository.save(img);
        }
        for (String hashTag : hashTags) {
            FeedHashTag feedHashTag = FeedHashTag.builder()
                    .feedNum(feed)
                    .feedHashTag(hashTag)
                    .build();
            feedHashTagRepository.save(feedHashTag);
        }
    }

    public Feed getFeed(String feedNum) {
        Feed feed = feedRepository.findByFeedNum(Integer.parseInt(feedNum));
        return feed;
    }

    public Map<String, Object> getFeedImgAndHashTag(String feedNum) {
        List<FeedImg> feedImg = feedImgRepository.findByFeedNum_FeedNum(Long.parseLong(feedNum));
        List<FeedHashTag> feedHashTag = feedHashTagRepository.findByFeedNum_FeedNum(Long.parseLong(feedNum));
        Map<String, Object> feedImgAndHashTag = new HashMap<String, Object>();
        feedImgAndHashTag.put("feedImg", feedImg);
        feedImgAndHashTag.put("feedHashTag", feedHashTag);
        return feedImgAndHashTag;
    }

    @Transactional
    public void feedUpdate(Map<String, Object> data, List<String> feedImgs, List<String> hashTag) {

        Feed feed = feedRepository.findByFeedNum(Integer.parseInt((String) data.get("feedNum")));
        feed.setFeedContent((String) data.get("contents"));
        feedRepository.save(feed);

        List<FeedImg> feedImg = feedImgRepository.findByFeedNum_FeedNum(Long.parseLong((String) data.get("feedNum")));
        for (FeedImg img : feedImg) {
            feedImgRepository.delete(img);
        }
        System.out.println(feedImgs);
        for (String feedImg2 : feedImgs) {
            FeedImg img = FeedImg.builder()
                    .feedNum(feed)
                    .feedImg(feedImg2)
                    .build();
            feedImgRepository.save(img);
        }

        List<FeedHashTag> feedHashTag = feedHashTagRepository
                .findByFeedNum_FeedNum(Long.parseLong((String) data.get("feedNum")));
        for (FeedHashTag hashTag2 : feedHashTag) {
            feedHashTagRepository.delete(hashTag2);
        }
        for (String hashTag2 : hashTag) {
            FeedHashTag feedHashTag2 = FeedHashTag.builder()
                    .feedNum(feed)
                    .feedHashTag(hashTag2)
                    .build();
            feedHashTagRepository.save(feedHashTag2);
        }
    }

    @Transactional
    public void feedDelete(String feedNum) {
        Feed feed = feedRepository.findByFeedNum(Integer.parseInt(feedNum));
        feedRepository.delete(feed);
    }

    public Map<String, Object> getMyFeedList(String nickname, String userId) {
        List<Feed> feed = feedRepository.findByMember_Nickname(nickname);
        List<FeedComment> feedComment = new ArrayList<>();
        List<FeedHashTag> feedHashTag = new ArrayList<>();
        List<FeedImg> feedImg = new ArrayList<>();
        List<Integer> feedLike = new ArrayList<>();
        List<Like> myFeedLikes = new ArrayList<>();
        for (Feed f : feed) {
            feedComment.addAll(feedCommentRepository.findByFeedNum_FeedNum(f.getFeedNum()));
            feedHashTag.addAll(feedHashTagRepository.findByFeedNum_FeedNum(f.getFeedNum()));
            feedImg.addAll(feedImgRepository.findByFeedNum_FeedNum(f.getFeedNum()));
            feedLike.add(feedLikeRepository.countByFeedNum_FeedNum(f.getFeedNum()));
            myFeedLikes.addAll(feedLikeRepository.findByFeedLikeBy_UserId(userId));
        }
        Map<String, Object> feedList = new HashMap<String, Object>();

        feedList.put("feed", feed);
        feedList.put("feedComment", feedComment);
        feedList.put("feedHashTag", feedHashTag);
        feedList.put("feedImg", feedImg);
        feedList.put("feedLike", feedLike);
        feedList.put("myFeedLikes", myFeedLikes);

        return feedList;
    }

}
