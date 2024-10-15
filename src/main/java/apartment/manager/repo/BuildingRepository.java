package apartment.manager.repo;

import apartment.manager.entity.Building;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingRepository extends BaseRepository<Building, Long> {
}
