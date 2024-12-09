package apartment.manager.presentation.models;

import jakarta.validation.constraints.Pattern;

import java.util.Date;
import java.util.List;

public class RentalDetailsDto {
    private Integer monthlyRentValue;
    private TenantDto tenant;
    @Pattern(regexp = "^(NIS|JOD|USD)$", message = "Currency must be either NIS, JOD, or USD")
    private String currency;
    private String contractPicture;
    private Date rentalStartDate;
    @Pattern(regexp = "^(YEARLY|MONTHLY|HALF_YEARLY)$", message = "Rental type must be either YEARLY, HALF_YEARLY, or MONTHLY")
    private String rentalType;
    private List<Date> paymentDueDates;


    public Integer getMonthlyRentValue() {
        return monthlyRentValue;
    }

    public void setMonthlyRentValue(Integer monthlyRentValue) {
        this.monthlyRentValue = monthlyRentValue;
    }

    public TenantDto getTenant() {
        return tenant;
    }

    public void setTenant(TenantDto tenant) {
        this.tenant = tenant;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getContractPicture() {
        return contractPicture;
    }

    public void setContractPicture(String contractPicture) {
        this.contractPicture = contractPicture;
    }

    public Date getRentalStartDate() {
        return rentalStartDate;
    }

    public void setRentalStartDate(Date rentalStartDate) {
        this.rentalStartDate = rentalStartDate;
    }

    public String getRentalType() {
        return rentalType;
    }

    public void setRentalType(String rentalType) {
        this.rentalType = rentalType;
    }

    public List<Date> getPaymentDueDates() {
        return paymentDueDates;
    }

    public void setPaymentDueDates(List<Date> paymentDueDates) {
        this.paymentDueDates = paymentDueDates;
    }
}