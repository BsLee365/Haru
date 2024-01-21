package kr.co.teamA.Haru.Repository.place;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.teamA.Haru.Entity.SubCategory;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, String> {
    SubCategory findBySubCategory(String subCategory);
}
