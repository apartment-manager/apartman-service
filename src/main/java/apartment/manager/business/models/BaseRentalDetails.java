package apartment.manager.business.models;

import apartment.manager.Utilities.WebConfig;
import apartment.manager.entity.Tenant;
import apartment.manager.entity.utils.Currency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.*;

@Embeddable
@AllArgsConstructor
public class BaseRentalDetails implements RentalDetails {
    private Date rentalStartDate;
    private Integer monthlyRentValue;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tenant_id", referencedColumnName = "id")
    private Tenant tenant;
    private Currency currency;
    private String contractPicture;
    private List<Date> paymentDueDates;
    private String rentalType;


    public BaseRentalDetails(Date rentalStartDate) {
        this.rentalStartDate = rentalStartDate;
    }

    public BaseRentalDetails() {
        // throw new RuntimeException("Empty constructor was created to follow JPA constraints for embedded classes, but it shouldn't be used because rental start date must exist to avoid errors.");
    }

    @PrePersist
    public void setDefaults() {
        if (this.currency == null) {
            this.currency = Currency.NIS;
        }
    }

    public int getNumberOfMonths() {
        return 1;
    }

    ;

    @Scheduled()
    @Override
    public void createPaymentDueDates() {
        paymentDueDates = new ArrayList<>();
        addTwoPaymentDueDates(rentalStartDate);
    }

    public void addTwoPaymentDueDates(Date startDate) {
        if (paymentDueDates == null) {
            throw new RuntimeException("Payment due dates are not set for a specific apartment");
        }
        if (startDate == null) {
            if (paymentDueDates.isEmpty())
                paymentDueDates.add(new Date());//TODO: remove this
            startDate = paymentDueDates.getLast();

        }
        TimeZone calendarTimeZone = TimeZone.getTimeZone(WebConfig.TIMEZONE);
        Calendar calendar = Calendar.getInstance(calendarTimeZone);
        calendar.setTime(startDate);
        calendar.add(Calendar.MONTH, getNumberOfMonths());
        paymentDueDates.add(calendar.getTime());
        calendar.add(Calendar.MONTH, getNumberOfMonths());
        paymentDueDates.add(calendar.getTime());
    }

    public List<Date> getPaymentDueDates() {
        return paymentDueDates;
    }

    public void setPaymentDueDates(List<Date> paymentDueDates) {
        this.paymentDueDates = paymentDueDates;
    }

    public void setPaymentDueDates() {
        throw new RuntimeException("Setting due date is permitted. Due dates are calculated on the creation of  the object. or updated using the crone job");
    }

    public Date getRentalStartDate() {
        return rentalStartDate;
    }

    public void setRentalStartDate(Date rentalStartDate) {
        this.rentalStartDate = rentalStartDate;
    }

    public Integer getMonthlyRentValue() {
        return monthlyRentValue;
    }

    public void setMonthlyRentValue(Integer monthlyRentValue) {
        this.monthlyRentValue = monthlyRentValue;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getContractPicture() {
        return contractPicture;
    }

    public void setContractPicture(String contractPicture) {
        this.contractPicture = contractPicture;
    }

    public String getRentalType() {
        return rentalType;
    }

    public void setRentalType(String rentalType) {
        this.rentalType = rentalType;
    }
}
