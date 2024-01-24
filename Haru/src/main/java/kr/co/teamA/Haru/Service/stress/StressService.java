package kr.co.teamA.Haru.Service.stress;

<<<<<<< HEAD
import kr.co.teamA.Haru.DTO.RecommendPlaceDTO;
import kr.co.teamA.Haru.DTO.StressDataDTO;
import kr.co.teamA.Haru.DTO.StressSaveDTO;
import kr.co.teamA.Haru.Entity.Member;
import kr.co.teamA.Haru.Entity.Place;
import kr.co.teamA.Haru.Entity.PlaceRecommendList;
import kr.co.teamA.Haru.Entity.StressData;
import kr.co.teamA.Haru.Repository.*;
import kr.co.teamA.Haru.security.filter.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
=======
import kr.co.teamA.Haru.security.filter.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
>>>>>>> yj_base

@Service
public class StressService {

<<<<<<< HEAD
    @Autowired
    private RecommendPlaceRepository recommendPlaceRepository;

    @Autowired
    private StressRepository stressRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    PlaceRecommendListRepository placeRecommendListRepository;

    @Autowired
    StressDataRepository stressDataRepository;

=======
>>>>>>> yj_base
    JwtTokenProvider jwtTokenProvider;
    public String tokenValidation(String token) {
        boolean verification = jwtTokenProvider.validateToken(token);
        if (verification) {
            return "success";
        } else {
            return "fail";
        }
    }
<<<<<<< HEAD

    // 회원이 추천 알고리즘
    public List<RecommendPlaceDTO> getRecommendPlaces(String userId, Double stressScore) {

        System.out.println("추천 비즈니스 로직 실행");

        // 카테고리, 사용자가 찜한건 제외
        List<RecommendPlaceDTO> Category = recommendPlaceRepository.recommendQuery(userId);
        // 찜 목록 유사한 장소, 사용자가 찜한 장소 제외, 추천 받았던 리스트도 제외
        List<RecommendPlaceDTO> Dibs = recommendPlaceRepository.recommendDibs(userId);

        // 사용자가 추천 받을 리스트
        ArrayList<RecommendPlaceDTO> recommendList = new ArrayList<>();

        // 장소를 추가해 줌.
        recommendList.addAll(Category);
        recommendList.addAll(Dibs);

        // 스트레스 기반 장소추출
        System.out.println("스트레스 점수 >> " + (int)Math.round(stressScore*10));
        List<RecommendPlaceDTO> filterList = recommendList.stream()
                .filter(item -> stressRecommend(item, (int)Math.round(stressScore*10)))
                .collect(Collectors.toList());

        List<RecommendPlaceDTO> save = null;

        // 만약 더 이상 추천 받을 리스트가 없다면
        if(filterList.isEmpty()){
            // 랜덤으로 섞인 배열 중 5개를 출력해서 준다.
            Collections.shuffle(Category);
            // 전체 카테고리를 추천해준다.
            save = recommendList.subList(0, Math.min(5, recommendList.size()));
            // DB에 저장
            saveRecommendPlace(save);
            return save;
        }
        else { // 추천 받을 리스트가 있다면
            Collections.shuffle(filterList);
            save = filterList.subList(0, Math.min(5, filterList.size()));
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

    // 스트레스 추이 서비스
    public List<StressDataDTO> getStressData(String userId, List<Object> list, String flag){
        System.out.println("스트레스 조회 비즈니스 로직");

        String oneday = (String)list.get(0);
        List<String> days = (List<String>) list.get(1);
        List<String> month = (List<String>)list.get(2);
        String selectYear = (String)list.get(3);

        System.out.println(oneday);
        System.out.println(days);
        System.out.println(month);
        System.out.println(selectYear);

        System.out.println("플래그" + flag); //day, days, month, year

        if(flag.equals("day")){
            return stressDataRepository.getStressDatabyDay(userId, oneday, oneday);
        }
        else if(flag.equals("days")){
            return stressDataRepository.getStressDatabyDay(userId, days.get(0), days.get(1));
        }
        else if(flag.equals("month")){
            return stressDataRepository.getStressDatabyMonth(userId, month.get(0), month.get(1));
        }
        else if(flag.equals("year")){
            return stressDataRepository.getStressDatabyYear(userId, selectYear);
        }
        else {
            return null;
        }

//        stressDataRepository.getStressDatabyDay();
//        stressDataRepository.getStressDatabyDays();
//        stressDataRepository.getStressDatabyMonth();
//        stressDataRepository.getStressDatabyYear();

    }

}
=======
}
>>>>>>> yj_base
