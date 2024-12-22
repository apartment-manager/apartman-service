package apartment.manager.entity;

import apartment.manager.entity.utils.Currency;
import apartment.manager.entity.utils.PaymentType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.Filter;

import java.util.Date;

@Entity
@Table(name = "payments")
@Filter(name = "userFilter", condition = "user_id  = :userId")
public class Payment extends BaseEntity {
    @Column
    private Long tenantId;
    @Column
    private Long apartmentId;
    @Column
    private Date paymentDate;
    @Column
    private Integer paymentCoverageStartDate;
    @Column
    private Integer paymentCoverageEndDate;
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

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
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

    public Integer getPaymentCoverageStartDate() {
        return paymentCoverageStartDate;
    }

    public void setPaymentCoverageStartDate(Integer paymentCoverageStartDate) {
        this.paymentCoverageStartDate = paymentCoverageStartDate;
    }

    public Integer getPaymentCoverageEndDate() {
        return paymentCoverageEndDate;
    }

    public void setPaymentCoverageEndDate(Integer paymentCoverageEndDate) {
        this.paymentCoverageEndDate = paymentCoverageEndDate;
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
