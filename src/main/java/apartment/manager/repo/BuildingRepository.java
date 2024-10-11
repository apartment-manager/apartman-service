package apartment.manager.repo;

import apartment.manager.model.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingRepository extends BaseRepository<Building, Long> {
}
