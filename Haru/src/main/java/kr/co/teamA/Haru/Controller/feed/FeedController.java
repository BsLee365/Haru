package kr.co.teamA.Haru.Controller.feed;

import java.util.Map;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.teamA.Haru.Entity.FeedComment;
import kr.co.teamA.Haru.Service.feed.FeedService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@RestController
public class FeedController {

    private final FeedService feedService;

    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }

    @GetMapping("/feedList")
    public Map<String, Object> getFeedList() {

        Map<String, Object> feedList = feedService.getFeedList();

        return feedList;
    }

    @PostMapping("/addFeedComment")
    public List<FeedComment> addFeedComment(@RequestParam Map<String, String> data) {
        int feedNum = Integer.parseInt(data.get("feedNum"));
        String userId = data.get("userId");
        String feedCommentContent = data.get("feedCommentContent");

        List<FeedComment> commentList = feedService.addFeedComment(feedNum, userId, feedCommentContent);

        return commentList;
    }

    @Transactional
    @PostMapping("/modifyFeedLike")
    public int modifyFeedLike(@RequestParam Map<String, String> data) {
        int feedNum = Integer.parseInt(data.get("feedNum"));
        String userId = data.get("userId");

        int likes = feedService.modifyFeedLike(feedNum, userId);

        return likes;
    }

}
