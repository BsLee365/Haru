package kr.co.teamA.Haru.Repository;

import kr.co.teamA.Haru.Entity.StressData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmortionalReportRepositroy extends JpaRepository<StressData, Long> {
    // 하루 (시간)
    // 일간
    // 월간
    // 연간
}
