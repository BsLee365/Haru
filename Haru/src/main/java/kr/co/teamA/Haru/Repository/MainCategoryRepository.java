package kr.co.teamA.Haru.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.teamA.Haru.Entity.MainCategory;

@Repository
public interface MainCategoryRepository extends JpaRepository<MainCategory, String> {

    MainCategory findByMainCategory(String mainCategory);

}
