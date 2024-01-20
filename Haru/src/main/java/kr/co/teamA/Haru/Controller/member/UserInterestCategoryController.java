package kr.co.teamA.Haru.Controller.member;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.teamA.Haru.Repository.UserInterestCategoryRepository;
import kr.co.teamA.Haru.Service.member.UserInterestCategoryService;

@RestController
public class UserInterestCategoryController {

    private final UserInterestCategoryService userInterestCategoryService;
    private final UserInterestCategoryRepository userInterestCategoryRepository;

    public UserInterestCategoryController(UserInterestCategoryService userInterestCategoryService,
            UserInterestCategoryRepository userInterestCategoryRepository) {
        this.userInterestCategoryRepository = userInterestCategoryRepository;
        this.userInterestCategoryService = userInterestCategoryService;
    }

    @PostMapping("/setHobby")
    public String setHobby(@RequestParam("id") String userId, @RequestParam("hobbyList") List<String> hobbyList) {
        System.out.println(userId);
        System.out.println(hobbyList);

        userInterestCategoryRepository.deleteByUserId(userId);

        for (String hobby : hobbyList) {
            userInterestCategoryService.insert(hobby, userId);
        }
        return userId + "님 관심 카테고리 설정 완료";
    }
}
