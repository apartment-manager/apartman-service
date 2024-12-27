package apartment.manager.Utilities.mappers;

import apartment.manager.entity.Payment;
import apartment.manager.presentation.models.PaymentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public abstract class PaymentMapper {

    public  abstract PaymentDto paymentToPaymentDto(Payment payment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "modifiedDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    public abstract Payment paymentDtoToPayment(PaymentDto paymentDto);

    public List<PaymentDto> allPaymentsToPaymentDto(List<Payment> payments) {
        return payments.stream().map(this::paymentToPaymentDto).collect(Collectors.toList());
    }

    public List<Payment> allPaymentDtoToPayment(List<PaymentDto> paymentsDto) {
        return paymentsDto.stream().map(this::paymentDtoToPayment).collect(Collectors.toList());
    }
}
