package kr.co.teamA.Haru.Service.member;

import org.springframework.stereotype.Service;

import kr.co.teamA.Haru.Entity.MainCategory;
import kr.co.teamA.Haru.Entity.Member;
import kr.co.teamA.Haru.Entity.UserInterestCategory;
import kr.co.teamA.Haru.Repository.MainCategoryRepository;
import kr.co.teamA.Haru.Repository.MemberRepository;
import kr.co.teamA.Haru.Repository.UserInterestCategoryRepository;

@Service
public class UserInterestCategoryService {

    private final UserInterestCategoryRepository userInterestCategoryRepository;
    private final MainCategoryRepository mainCategoryRepository;
    private final MemberRepository memberRepository;

    public UserInterestCategoryService(UserInterestCategoryRepository userInterestCategoryRepository,
            MainCategoryRepository mainCategoryRepository, MemberRepository memberRepository) {
        this.userInterestCategoryRepository = userInterestCategoryRepository;
        this.mainCategoryRepository = mainCategoryRepository;
        this.memberRepository = memberRepository;
    }

    public UserInterestCategory insert(String hobby, String userId) {

        MainCategory mainCategory = mainCategoryRepository.findByMainCategory(hobby);
        Member member = memberRepository.findMemberByuserId(userId);

        UserInterestCategory entity = UserInterestCategory.builder()
                .userId(member)
                .mainCategory(mainCategory)
                .build();

        return userInterestCategoryRepository.save(entity);
    }
}
