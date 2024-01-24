package kr.co.teamA.Haru.Service.myPage;

<<<<<<< HEAD
import java.util.Map;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import kr.co.teamA.Haru.Entity.Alarm;
import kr.co.teamA.Haru.Entity.Member;
import java.util.List;
import java.util.HashMap;
import kr.co.teamA.Haru.Repository.MemberRepository;
import kr.co.teamA.Haru.Repository.member.AlarmRepository;
import kr.co.teamA.Haru.security.filter.JwtTokenProvider;

@Service
public class MyPageService {

    public final MemberRepository memberRepository;
    public final AlarmRepository alarmRepository;

    public MyPageService(MemberRepository memberRepository, AlarmRepository alarmRepository) {
        this.memberRepository = memberRepository;
        this.alarmRepository = alarmRepository;
    }

    JwtTokenProvider jwtTokenProvider;

=======
import kr.co.teamA.Haru.security.filter.JwtTokenProvider;

public class MyPageService {

    JwtTokenProvider jwtTokenProvider;
>>>>>>> yj_base
    public String myPageData(String token) {
        if (jwtTokenProvider.validateToken(token)) {
            return "false";
        }
<<<<<<< HEAD
        // String data = jwtTokenProvider.getUsername(token);
        // System.out.println(data);
        return "data";

    }

    @Transactional
    public Map<String, Object> getMyPageData(String userId) {
        Member member = memberRepository.findMemberByuserId(userId);
        System.out.println("member : " + member);
        List<Alarm> alarmList = alarmRepository.findAllByMember_UserId(userId);
        int likeCount = alarmRepository.countByMember_UserIdAndLike_LikeNumIsNotNull(userId);
        int commentCount = alarmRepository.countByMember_UserIdAndFeedComment_CommentNumIsNotNull(userId);
        System.out.println(likeCount);
        System.out.println(commentCount);
        Map<String, Object> myPageData = new HashMap<>();
        try {
            myPageData.put("member", member);
            myPageData.put("alarmList", alarmList);
            myPageData.put("likeCount", likeCount);
            myPageData.put("commentCount", commentCount);
        } catch (Exception e) {
            System.out.println("알람이 없습니다.");
            myPageData.put("member", member);
            myPageData.put("alarmList", "None");
            myPageData.put("likeCount", likeCount);
            myPageData.put("commentCount", commentCount);
        }
        return myPageData;
    }
=======
//        String data = jwtTokenProvider.getUsername(token);
//        System.out.println(data);
        return "data";

    }
>>>>>>> yj_base
}
