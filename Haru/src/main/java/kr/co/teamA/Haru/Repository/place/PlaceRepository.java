package kr.co.teamA.Haru.Repository.place;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.teamA.Haru.Entity.Place;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    Place findByPlaceNum(Long placeNum);
}
