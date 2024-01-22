package kr.co.teamA.Haru.Service.stress;

import kr.co.teamA.Haru.DTO.RecommendPlaceDTO;
import kr.co.teamA.Haru.DTO.StressSaveDTO;
import kr.co.teamA.Haru.Entity.Member;
import kr.co.teamA.Haru.Entity.Place;
import kr.co.teamA.Haru.Entity.PlaceRecommendList;
import kr.co.teamA.Haru.Entity.StressData;
import kr.co.teamA.Haru.Repository.MemberRepository;
import kr.co.teamA.Haru.Repository.PlaceRecommendListRepository;
import kr.co.teamA.Haru.Repository.RecommendPlaceRepository;
import kr.co.teamA.Haru.Repository.StressRepository;
import kr.co.teamA.Haru.security.filter.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StressService {

    @Autowired
    private RecommendPlaceRepository recommendPlaceRepository;

    @Autowired
    private StressRepository stressRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    PlaceRecommendListRepository placeRecommendListRepository;

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
    public List<RecommendPlaceDTO> getRecommendPlaces(String userId, Double stressScore) {

        System.out.println("비즈니스 로직 실행");

        // 카테고리
        List<RecommendPlaceDTO> Category = recommendPlaceRepository.recommendQuery(userId);
        // 찜 목록 유사한 장소 (사용자가 찜한 장소 제외)
        List<RecommendPlaceDTO> Dibs = recommendPlaceRepository.recommendDibs(userId);
        // 찜목록
        List<RecommendPlaceDTO> Dibslist = recommendPlaceRepository.DibsList(userId);
        
        // 스트레스 기반 장소추천, 첫 리뷰와 끝 리뷰 숫자 정해주는 메소드
        // 모든 장소 리스트
        ArrayList<RecommendPlaceDTO> recommendList = new ArrayList<>();

        // 장소를 추가해 줌.
        recommendList.addAll(Category);
        recommendList.addAll(Dibs);
        recommendList.addAll(Dibslist);

        // 스트레스 기반 장소추출
        System.out.println("스트레스 점수 >> " + (int)Math.round(stressScore*10));
        List<RecommendPlaceDTO> filterList = recommendList.stream()
                .filter(item -> stressRecommend(item, (int)Math.round(stressScore*10)))
                .collect(Collectors.toList());

        // filterList 에서 없으면, 카테고리하고 찜 리스트에서
        // 랜덤으로 섞인 배열 중 5개를 출력해서 준다.
        if(filterList.isEmpty()){
            // 스트레스 수치에서 필터링 장소가 없을 경우
            Collections.shuffle(recommendList);// 메모리 안에서 랜덤으로 섞어준다.

            List<RecommendPlaceDTO> save = recommendList.subList(0, Math.min(5, recommendList.size()));

            //DB 저장
            saveRecommendPlace(save);

            return save;
        }
        else{
            // 스트레스 수치에서 필터링하여 장소가 있을경우,
            //Collections.shuffle(filterList);// 메모리 안에서 랜덤으로 섞어준다.

            List<RecommendPlaceDTO> save = filterList.subList(0, Math.min(5, filterList.size()));

            //DB 저장
            saveRecommendPlace(save);

            return save;
        }

    }
    // DB에 추천 받은 장소 저장
    @Transactional
    public void saveRecommendPlace(List<RecommendPlaceDTO> vo){

        // 효율적으로 처리하기 위한 ArrayList
        List<PlaceRecommendList> placeRecommendListList = new ArrayList<>();
        for (RecommendPlaceDTO list : vo){
            Long placeNum = Long.parseLong(list.getPlaceNum());
            String userId = list.getUserId();

            PlaceRecommendList placeRecommendList = new PlaceRecommendList();

            recommendPlaceRepository.findById(placeNum).ifPresent(placeRecommendList::setPlace);
            memberRepository.findById(userId).ifPresent(placeRecommendList::setMember);
            placeRecommendList.setPlaceCdate(new Date());

            // 모두 add.
            placeRecommendListList.add(placeRecommendList);
        }
        // DB에 저장
        placeRecommendListRepository.saveAll(placeRecommendListList);
    }

    // 스트레스 기반 장소추천 method
    public boolean stressRecommend(RecommendPlaceDTO item, int stressScore){

        // 장소 리뷰 점수
        int score = item.getPlaceScore();

        if (0 <= stressScore &&  stressScore <= 20){
            // 0 - 20
            return score >= 0 && score <= 20;
        }
        else if (21 <= stressScore &&  stressScore <= 40) {
            // 21 - 40
            return score >= 21 && score <= 40;
        }
        else if (41 <= stressScore &&  stressScore <= 60) {
            // 41 - 60
            return score >= 41 && score <= 60;
        }
        else if (61 <= stressScore && stressScore <= 80) {
            // 61 - 80
            return score >= 61 && score <= 80;
        }
        else if (81 <= stressScore && stressScore <= 100) {
            // 81 - 100
            return score >= 81 && score <= 100;
        }
        else {
            return false;
        }
    }

    // 스트레스 데이터 저장
    @Transactional
    public void saveStressData(StressSaveDTO stressData){

        System.out.println(stressData.toString());
        StressData vo = new StressData();

        //
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