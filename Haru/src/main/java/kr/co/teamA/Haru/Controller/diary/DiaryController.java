package kr.co.teamA.Haru.Controller.diary;
import kr.co.teamA.Haru.DTO.DiaryDTO;
import kr.co.teamA.Haru.Service.diary.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiaryController {

    @Autowired
    private DiaryService diaryService;

    // 일기 저장.
    @PostMapping("/stress/saveDiary")
        public DiaryDTO saveDiary(@RequestBody DiaryDTO diary){
        diaryService.saveDiary(diary);
        return diary;
    }
}
