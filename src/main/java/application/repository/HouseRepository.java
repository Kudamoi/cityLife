package application.repository;

import application.models.HouseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HouseRepository extends JpaRepository<HouseModel, Long> {
    @Query(value ="select * from houses where owner_id = :user_id", nativeQuery = true)
    List<HouseModel> getUserHouses(Long user_id);

    @Query(value ="select * from houses where owner_id = :user_id and id = :id", nativeQuery = true)
    Optional<HouseModel> getUserHouse(Long id, Long user_id);

    @Query(value ="delete from houses where owner_id = :user_id and id = :id", nativeQuery = true)
    ResponseEntity<?> deleteUserHouse(Long id, Long user_id);
}
