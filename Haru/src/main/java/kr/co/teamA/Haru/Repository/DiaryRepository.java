package kr.co.teamA.Haru.Repository;

import kr.co.teamA.Haru.Entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {

}
