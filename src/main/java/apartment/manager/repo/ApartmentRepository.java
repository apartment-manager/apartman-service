package apartment.manager.repo;

import apartment.manager.entity.Apartment;
import apartment.manager.entity.Building;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApartmentRepository extends BaseRepository<Apartment, Long> {
    List<Apartment> findByBuildingIdAndCreatedBy(Long buildingId, Long userId, Sort sort);

    Long countByBuildingAndCreatedBy(Building buildingId, Long userId);

    List<Apartment> findByNameAndCreatedBy(Integer query, Long userId);

    Optional<Apartment> findByIdAndCreatedBy(Long id, Long userId);

    List<Apartment> findAllByCreatedBy(Long userId);

    Integer countByIsAvailableFalseAndCreatedBy(Long userId);

    Integer countByIsAvailableTrueAndCreatedBy(Long userId);

}
