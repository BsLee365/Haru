package kr.co.teamA.Haru.Repository;

import java.util.List;

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


    // 유저 아이디로 관심 카테고리 삭제
    @Transactional
    @Modifying
    @Query("delete from UserInterestCategory uic where uic.userId.userId = :userId")
    void deleteByUserId(@Param("userId") String userId);

    List<UserInterestCategory> findByUserId_UserId(String userId);
}
