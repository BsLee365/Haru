package kr.co.teamA.Haru.Controller.feed;

import java.util.Map;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import kr.co.teamA.Haru.Entity.Feed;
import kr.co.teamA.Haru.Entity.FeedComment;
import kr.co.teamA.Haru.Service.feed.FeedService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.util.List;
import java.util.ArrayList;

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

    @PostMapping("/uploadFeed")
    public void uploadFeed(@RequestParam Map<String, Object> data, @RequestParam("file") List<MultipartFile> files,
            @RequestParam("hashTag") List<String> hashTag) {
        System.out.println(data);
        List<String> feedImgs = new ArrayList<>();
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                saveFile(file);
                feedImgs.add(file.getOriginalFilename());
            }
        }
        feedService.uploadFeed(data, feedImgs, hashTag);

    }

    private void saveFile(MultipartFile file) {
        try {
            String rootPath = System.getProperty("user.dir");
            System.out.println(rootPath);
            String uploadDir = rootPath + "\\Haru\\src\\main\\resources\\static\\FeedImg";
            String fileName = file.getOriginalFilename();

            file.transferTo(new File(uploadDir + File.separator + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/feedUpdateForm")
    public Feed feedUpdateForm(@RequestParam("feedNum") String feedNum) {
        Feed feed = feedService.getFeed(feedNum);
        System.out.println(feed);
        return feed;
    }

    @PostMapping("/getFeedImgAndHashTag")
    public Map<String, Object> getFeedImgAndHashTag(@RequestParam("feedNum") String feedNum) {
        Map<String, Object> feedImgAndHashTag = feedService.getFeedImgAndHashTag(feedNum);

        return feedImgAndHashTag;
    }

    @PostMapping("/updateFeed")
    public void feedUpdate(@RequestParam Map<String, Object> data,
            @RequestParam(value = "file", required = false) List<MultipartFile> files,
            @RequestParam("hashTag") List<String> hashTag,
            @RequestParam(value = "imageName", required = false) List<String> imageName) {
        System.out.println(data);
        List<String> feedImgs = new ArrayList<>();
        if (files != null) {
            for (MultipartFile file : files) {
                saveFile(file);
            }
        }
        for (String file : imageName) {
            feedImgs.add(file);
        }
        feedService.feedUpdate(data, feedImgs, hashTag);
    }

    @PostMapping("/feedDelete")
    public void feedDelete(@RequestParam("feedNum") String feedNum) {
        feedService.feedDelete(feedNum);
    }

    @PostMapping("/myFeedList")
    public Map<String, Object> myFeedList(@RequestParam("nickname") String nickname) {

        Map<String, Object> myFeedList = feedService.getMyFeedList(nickname);

        return myFeedList;
    }

}
