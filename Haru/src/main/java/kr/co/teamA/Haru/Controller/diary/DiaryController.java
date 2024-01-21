package kr.co.teamA.Haru.Controller.diary;
import kr.co.teamA.Haru.DTO.DiaryDTO;
import kr.co.teamA.Haru.Service.diary.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/diary")
public class DiaryController {

    @Autowired
    private DiaryService diaryService;

    // 일기 저장.
    @PostMapping("/saveDiary")
        public DiaryDTO saveDiary(@RequestBody DiaryDTO diary){
        System.out.println("다이어리 컨트롤러 실행");
        System.out.println(diary.toString());
        diaryService.saveDiary(diary);
        return diary;
    }
}
