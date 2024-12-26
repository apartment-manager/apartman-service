package apartment.manager.repo;

import apartment.manager.entity.Payment;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends BaseRepository<Payment, Long> {
    List<Payment> findByApartmentIdAndYearAndUserId(Long apartmentId, int year, Long userId, Sort sort);
    Optional<Payment> findByIdAndUserId(Long id, Long userId);
    void deleteAllByApartmentId(Long apartmentId);
}