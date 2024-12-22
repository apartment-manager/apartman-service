package apartment.manager.repo;

import apartment.manager.entity.Payment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends BaseRepository<Payment, Long> {
    List<Payment> findByApartmentIdAndUserId(Long apartmentId, Long userId);
    Optional<Payment> findByIdAndUserId(Long id, Long userId);

    void deleteAllByApartmentId(Long apartmentId);
}