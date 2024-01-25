package kr.co.teamA.Haru.Controller.feed;

import java.util.Map;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.co.teamA.Haru.Entity.Feed;
import kr.co.teamA.Haru.Entity.FeedComment;
import kr.co.teamA.Haru.Service.feed.FeedService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.FileOutputStream;
import java.util.List;
import java.util.ArrayList;

@RestController
public class FeedController {

    private final FeedService feedService;

    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }

    @PostMapping("/feedList")
    public Map<String, Object> getFeedList(@RequestParam("userId") String userId) {

        Map<String, Object> feedList = feedService.getFeedList(userId);

        return feedList;
    }

    @PostMapping("/addFeedComment")
    public List<FeedComment> addFeedComment(@RequestParam Map<String, String> data) {
        Long feedNum = Long.parseLong(data.get("feedNum"));
        String userId = data.get("userId");
        String feedCommentContent = data.get("feedCommentContent");
        String feedUserId = data.get("feedUserId");

        List<FeedComment> commentList = feedService.addFeedComment(feedNum, userId, feedCommentContent, feedUserId);

        return commentList;
    }

    @Transactional
    @PostMapping("/modifyFeedLike")
    public int modifyFeedLike(@RequestParam Map<String, String> data) {
        Long feedNum = Long.parseLong(data.get("feedNum"));
        String userId = data.get("userId");
        String feedUserId = data.get("feedUserId");

        int likes = feedService.modifyFeedLike(feedNum, userId, feedUserId);

        return likes;
    }

    @PostMapping("/uploadFeed")
    public void uploadFeed(@RequestParam Map<String, Object> data, @RequestParam("file") List<MultipartFile> files,
            @RequestParam("hashTag") List<String> hashTag) {
        System.out.println(data);
        List<String> feedImgs = new ArrayList<>();
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                String imgName = saveFile(file);
                feedImgs.add(imgName);
            }
        }
        feedService.uploadFeed(data, feedImgs, hashTag);

    }

    private String saveFile(MultipartFile multipartFile) {
        String imgName = null;
        imgName = "FeedImg" + UUID.randomUUID() + getExtension(multipartFile);
        String rootPath = System.getProperty("user.dir");
        System.out.println(rootPath);
        String uploadPath = rootPath + "\\Haru\\src\\main\\resources\\static\\img\\Feed\\" + imgName;
        System.out.println(uploadPath);
        try (FileOutputStream writer = new FileOutputStream(uploadPath)) {
            writer.write(multipartFile.getBytes());
            return imgName;
        } catch (Exception e) {
            // log.error(e.getMessage(), e);
            throw new RuntimeException("Fail to upload files.");
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
    public Map<String, Object> myFeedList(@RequestParam("nickname") String nickname,
            @RequestParam("userId") String userId) {

        Map<String, Object> myFeedList = feedService.getMyFeedList(nickname, userId);

        return myFeedList;
    }

    private String getExtension(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        int index = fileName.indexOf(".");
        if (index > -1) {
            return fileName.substring(index);
        }
        return "";
    }

}
