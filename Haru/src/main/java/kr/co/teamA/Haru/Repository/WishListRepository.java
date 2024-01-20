package kr.co.teamA.Haru.Repository;

import io.lettuce.core.dynamic.annotation.Param;
import kr.co.teamA.Haru.DTO.WishListDTO;
import kr.co.teamA.Haru.Entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WishListRepository extends JpaRepository<WishList, Long> {

    @Query("SELECT w FROM WishList w WHERE w.member.userId = :userId")
    List<WishListDTO> findByUserId(@Param("userId") String userId);
}
