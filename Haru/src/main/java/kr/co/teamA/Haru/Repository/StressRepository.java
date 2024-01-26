package kr.co.teamA.Haru.Repository;

import kr.co.teamA.Haru.Entity.StressData;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StressRepository extends JpaRepository<StressData, Long> {
    List<StressData> findAll();
}
