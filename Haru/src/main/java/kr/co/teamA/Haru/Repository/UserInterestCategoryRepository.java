package kr.co.teamA.Haru.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import kr.co.teamA.Haru.Entity.UserInterestCategory;

@Repository
public interface UserInterestCategoryRepository extends JpaRepository<UserInterestCategory, Long> {

    @Query("SELECT uic FROM UserInterestCategory uic WHERE uic.userId = :userId AND uic.mainCategory = :mainCategory")
    UserInterestCategory findByUserIdAndMainCategory(@Param("userId") String userId,
            @Param("mainCategory") String mainCategory);

    @Transactional
    @Modifying
    @Query("delete from UserInterestCategory uic where uic.userId.userId = :userId")
    void deleteByUserId(@Param("userId") String userId);
}
