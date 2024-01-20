package kr.co.teamA.Haru.Service.stress;

import kr.co.teamA.Haru.DTO.RecommendPlaceDTO;
import kr.co.teamA.Haru.DTO.StressSaveDTO;
import kr.co.teamA.Haru.Entity.Member;
import kr.co.teamA.Haru.Entity.StressData;
import kr.co.teamA.Haru.Repository.MemberRepository;
import kr.co.teamA.Haru.Repository.RecommendPlaceRepository;
import kr.co.teamA.Haru.Repository.StressRepository;
import kr.co.teamA.Haru.security.filter.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class StressService {

    @Autowired
    private RecommendPlaceRepository recommendPlaceRepository;

    @Autowired
    private StressRepository stressRepository;

    @Autowired
    private MemberRepository memberRepository;

    JwtTokenProvider jwtTokenProvider;
    public String tokenValidation(String token) {
        boolean verification = jwtTokenProvider.validateToken(token);
        if (verification) {
            return "success";
        } else {
            return "fail";
        }
    }

    // 회원이 추천 알고리즘
    public List<RecommendPlaceDTO> getRecommendPlaces(String userId) {

        // 카테고리
        List<RecommendPlaceDTO> Category = recommendPlaceRepository.recommendQuery(userId);
        // 찜 목록, 여기가 문제
        List<RecommendPlaceDTO> Bid = recommendPlaceRepository.recommendDibs(userId);

        ArrayList<RecommendPlaceDTO> recommendList = new ArrayList<>();

        recommendList.addAll(Category);
        recommendList.addAll(Bid);

        Collections.shuffle(recommendList);

        return recommendList.subList(0, Math.min(5, recommendList.size()));
    }

    // 스트레스 데이터 저장
    @Transactional
    public void saveStressData(StressSaveDTO stressData){

        System.out.println(stressData.toString());
        StressData vo = new StressData();

        Optional<Member> mem = memberRepository.findByUserId(stressData.getUserid());

        //유저 아이디
        mem.ifPresent(vo::setMember);
        //얼굴
        vo.setFaceData(stressData.getFacefigure());
        //일기
        vo.setDiaryData(stressData.getDiaryfigure());
        //총 점수
        vo.setStressScore(stressData.getStressscore());
        //날짜
        vo.setStressCdate(new Date());
        //DB에 저장
        stressRepository.save(vo);
    }

}
