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
<<<<<<< HEAD
    public DiaryDTO saveDiary(@RequestBody DiaryDTO diary){
=======
        public DiaryDTO saveDiary(@RequestBody DiaryDTO diary){
        System.out.println("다이어리 컨트롤러 실행");
        System.out.println(diary.toString());
>>>>>>> origin/BeomSeok_ori
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
