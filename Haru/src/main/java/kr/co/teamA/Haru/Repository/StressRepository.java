package kr.co.teamA.Haru.Repository;

import kr.co.teamA.Haru.Entity.StressData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StressRepository extends JpaRepository<StressData, Long> {
}
