package apartment.manager.repo;

import apartment.manager.entity.Apartment;
import apartment.manager.entity.Building;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApartmentRepository extends BaseRepository<Apartment, Long> {
    List<Apartment> findByBuildingId(Long buildingId);
    Long countByBuildingAndUserId(Building buildingId, Long userId);
    List<Apartment> findByNameContainingIgnoreCase(String query);
}
