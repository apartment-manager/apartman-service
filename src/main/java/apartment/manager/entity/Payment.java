package apartment.manager.entity;

import apartment.manager.entity.utils.Currency;
import apartment.manager.entity.utils.PaymentType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import org.hibernate.annotations.Filter;

import java.util.Date;

@Entity
@Table(name = "payments")
@Filter(name = "userFilter", condition = BaseEntity.CREATED_BY_DATABASE_PROPERTY + "  = :createdBy")
public class Payment extends BaseEntity {
    public static final String MONTH_PAYMENT_PROPERTY = "month";
    public static final String YEAR_PAYMENT_PROPERTY = "YEAR";
    @Column
    private Long apartmentId;
    @Column
    private Date paymentDate;
    @Column
    private Integer month;
    @Column
    private Integer year;
    @Column
    private PaymentType paymentType;
    @Column
    private Double value;
    @Column
    private Currency currency;
    @Column
    private String notes;
    @Column
    private Long chequeNumber;
    @Column
    private Date chequeDueDate;

    @PrePersist
    private void setDefaults() {
        if (paymentDate == null) {
            setPaymentDate(new Date());
        }
    }

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
