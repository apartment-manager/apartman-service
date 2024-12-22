package apartment.manager.repo;

import apartment.manager.entity.Building;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BuildingRepository extends BaseRepository<Building, Long> {
    Optional<Building> findByIdAndUserId(Long id, Long userId);
    List<Building> findAllByUserId(Long userId);
}
