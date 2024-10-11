package apartment.manager.repo;

import apartment.manager.model.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartmentRepository extends BaseRepository<Apartment, Long> {
}
