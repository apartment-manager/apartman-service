package apartment.manager.business;

import apartment.manager.Utilities.JwtAuthenticationFilter;
import apartment.manager.Utilities.mappers.PaymentMapper;
import apartment.manager.Utilities.models.GlobalException;
import apartment.manager.Utilities.models.GlobalExceptionCode;
import apartment.manager.entity.Payment;
import apartment.manager.presentation.models.PaymentDto;
import apartment.manager.repo.PaymentRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PaymentService { //TODO: implement service level validation for entities
    private final PaymentRepository paymentRepository;
    @Autowired
    private final PaymentMapper paymentMapper;
    @Autowired
    HttpSession session;

    public PaymentService(PaymentRepository paymentRepository, PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }

    public PaymentDto createPayment(PaymentDto paymentDto) {
        Payment payment = paymentMapper.paymentDtoToPayment(paymentDto);
        return paymentMapper.paymentToPaymentDto(paymentRepository.save(payment));
    }

    public PaymentDto getPaymentById(Long id) {
        Payment payment = paymentRepository.findByIdAndCreatedBy(id, (Long) session.getAttribute(JwtAuthenticationFilter.USER_ID_SESSION_ATTRIBUTE)).orElseThrow(() -> new GlobalException("Couldn't find Payment with id: {" + id + "}", GlobalExceptionCode.RESOURCE_NOT_FOUND, NoSuchElementException.class));
        return paymentMapper.paymentToPaymentDto(payment);
    }

    public List<PaymentDto> getYearlyPaymentsByApartmentId(Long id, int year) {
//        if (!apartmentService.isExist(id)) {
//            throw new GlobalException("Couldn't find an apartment with id: {" + id + "}", GlobalExceptionCode.RESOURCE_BUILDING_NOT_FOUND, NoSuchElementException.class);
//        }
        Sort sort = Sort.by(Sort.Direction.DESC, Payment.MONTH_PAYMENT_PROPERTY);
        List<Payment> payments = paymentRepository.findByApartmentIdAndYearAndCreatedBy(id, year, (Long) session.getAttribute(JwtAuthenticationFilter.USER_ID_SESSION_ATTRIBUTE), sort);
        return paymentMapper.allPaymentsToPaymentDto(payments);
    }

    public PaymentDto updatePayment(PaymentDto paymentDto, Long id) {
        Payment oldPayment = paymentRepository.findByIdAndCreatedBy(id, (Long) session.getAttribute(JwtAuthenticationFilter.USER_ID_SESSION_ATTRIBUTE)).orElseThrow(() -> new GlobalException("Couldn't find a payment with id: {" + id + "}", GlobalExceptionCode.RESOURCE_NOT_FOUND, NoSuchElementException.class));
        Payment updatedPayment = paymentMapper.paymentDtoToPayment(paymentDto);

        updatedPayment.setId(id);
        updatedPayment.setCreateDate(oldPayment.getCreateDate());
        updatedPayment.setModifiedDate(oldPayment.getModifiedDate());
        updatedPayment.setCreatedBy(oldPayment.getCreatedBy());

        Payment savedPayment = paymentRepository.save(updatedPayment);
        return paymentMapper.paymentToPaymentDto(savedPayment);
    }

    public void deletePayment(Long id) {
        if (!isExist(id)) {
            throw new GlobalException("Couldn't find a payment with id: {" + id + "}", GlobalExceptionCode.RESOURCE_NOT_FOUND, NoSuchElementException.class);
        }
        paymentRepository.deleteById(id);
    }

    public void deleteApartmentPayments(Long apartmentId) {
//        if (!apartmentService.isExist(apartmentId)) {
//            throw new GlobalException("Trying to delete payments for apartment {" + apartmentId + "} which doesn't exist", GlobalExceptionCode.VALIDATION, ValidationException.class);
//        }
        paymentRepository.deleteAllByApartmentId(apartmentId);
    }


    public boolean isExist(long id) {
        return paymentRepository.existsById(id);
    }
}
