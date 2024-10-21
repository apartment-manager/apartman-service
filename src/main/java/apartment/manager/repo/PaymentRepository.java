package apartment.manager.repo;

import apartment.manager.entity.Payment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends BaseRepository<Payment, Long> {
    List<Payment> findByApartmentId(Long apartmentId);
}