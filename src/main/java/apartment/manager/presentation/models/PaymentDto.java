package apartment.manager.presentation.models;

import apartment.manager.entity.utils.Currency;
import apartment.manager.entity.utils.PaymentType;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class PaymentDto extends BaseDto {
    @NotNull(message = "Payment 'apartmentId' is a required field")
    private Long apartmentId;
    private Date paymentDate;
    @NotNull(message = "Payment 'month' is a required field")
    private Integer month;
    @NotNull(message = "Payment 'year' is a required field")
    private Integer year;
    @NotNull(message = "Payment 'paymentType' is a required field")
    private PaymentType paymentType;
    @NotNull(message = "Payment 'value' is a required field")
    private Double value;
    private Currency currency;
    private String notes;
    private Long chequeNumber;
    private Date chequeDueDate;

    public Long getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(Long apartmentId) {
        this.apartmentId = apartmentId;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getChequeNumber() {
        return chequeNumber;
    }

    public void setChequeNumber(Long chequeNumber) {
        this.chequeNumber = chequeNumber;
    }

    public Date getChequeDueDate() {
        return chequeDueDate;
    }

    public void setChequeDueDate(Date chequeDueDate) {
        this.chequeDueDate = chequeDueDate;
    }
}
