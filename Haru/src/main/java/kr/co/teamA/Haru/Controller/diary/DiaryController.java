package kr.co.teamA.Haru.Controller.diary;
import kr.co.teamA.Haru.DTO.DiaryDTO;
import kr.co.teamA.Haru.Service.diary.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/diary")
public class DiaryController {

    @Autowired
    private DiaryService diaryService;

    // 일기 저장.
    @PostMapping("/saveDiary")
    public DiaryDTO saveDiary(@RequestBody DiaryDTO diary){
        diaryService.saveDiary(diary);
        return diary;
    }

    // 일기 수정 - 이리수
    @PostMapping("/updateDiary")
    public void updateDiary(@RequestBody DiaryDTO diary) {
        diaryService.updateDiary(diary);
    }

    // 일기 삭제 - 이리수
    @PostMapping("/deleteDiary/{diaryNum}")
    public void deleteDiary(@PathVariable Long diaryNum) {
        diaryService.deleteDiary(diaryNum);
    }
}
