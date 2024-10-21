package apartment.manager.business;

import apartment.manager.Utilities.mappers.PaymentMapper;
import apartment.manager.Utilities.models.GlobalException;
import apartment.manager.Utilities.models.GlobalExceptionCode;
import apartment.manager.entity.Payment;
import apartment.manager.presentation.models.PaymentDto;
import apartment.manager.repo.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PaymentService { //TODO: implement service level validation for entities
    private final PaymentRepository paymentRepository;
    private final ApartmentService apartmentService;
    @Autowired
    private final PaymentMapper paymentMapper;

    public PaymentService(PaymentRepository paymentRepository, ApartmentService apartmentService, PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.apartmentService = apartmentService;
        this.paymentMapper = paymentMapper;
    }

    public PaymentDto createPayment(PaymentDto paymentDto) {
        Payment payment = paymentMapper.paymentDtoToPayment(paymentDto);
        return paymentMapper.paymentToPaymentDto(paymentRepository.save(payment));
    }

    public PaymentDto getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Couldn't find a payment with id :" + id));
        return paymentMapper.paymentToPaymentDto(payment);
    }

    public List<PaymentDto> getPaymentsByApartmentId(Long id) {
        if (!apartmentService.isExist(id)) {
            throw new GlobalException("Couldn't find an apartment with id: {" + id + "}", GlobalExceptionCode.RESOURCE_BUILDING_NOT_FOUND, NoSuchElementException.class);
        }
        return paymentMapper.allPaymentsToPaymentDto(paymentRepository.findByApartmentId(id));
    }

    public PaymentDto updatePayment(PaymentDto paymentDto, Long id) {
        Payment oldPayment = paymentRepository.findById(id).orElseThrow(() -> new GlobalException("Couldn't find a payment with id: {" + id + "}", GlobalExceptionCode.RESOURCE_BUILDING_NOT_FOUND, NoSuchElementException.class));
        Payment updatedPayment = paymentMapper.paymentDtoToPayment(paymentDto);

        updatedPayment.setId(id);
        updatedPayment.setCreateDate(oldPayment.getCreateDate());
        updatedPayment.setModifiedDate(oldPayment.getModifiedDate());
        updatedPayment.setUserId(oldPayment.getUserId());

        Payment savedPayment = paymentRepository.save(updatedPayment);
        return paymentMapper.paymentToPaymentDto(savedPayment);
    }

    public void deletePayment(Long id) {
        if (!isExist(id)) {
            throw new GlobalException("Couldn't find a payment with id: {" + id + "}", GlobalExceptionCode.RESOURCE_BUILDING_NOT_FOUND, NoSuchElementException.class);
        }
        paymentRepository.deleteById(id);
    }


    public boolean isExist(long id) {
        return paymentRepository.existsById(id);
    }
}
