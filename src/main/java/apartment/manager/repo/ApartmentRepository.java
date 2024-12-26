package apartment.manager.repo;

import apartment.manager.entity.Apartment;
import apartment.manager.entity.Building;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApartmentRepository extends BaseRepository<Apartment, Long> {
    List<Apartment> findByBuildingIdAndUserId(Long buildingId, Long userId);

    Long countByBuildingAndUserId(Building buildingId, Long userId);

    List<Apartment> findByNameContainingIgnoreCaseAndUserId(String query, Long userId);

    Optional<Apartment> findByIdAndUserId(Long id, Long userId);

    List<Apartment> findAllByUserId(Long userId);

    Integer countByIsAvailableFalseAndUserId(Long userId);

    Integer countByIsAvailableTrueAndUserId(Long userId);

}
