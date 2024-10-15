package apartment.manager.repo;

import apartment.manager.entity.Apartment;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartmentRepository extends BaseRepository<Apartment, Long> {
}
