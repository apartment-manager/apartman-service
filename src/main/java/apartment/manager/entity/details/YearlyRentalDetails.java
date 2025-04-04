package apartment.manager.entity.details;

import jakarta.persistence.Embeddable;

import java.util.Date;

@Embeddable
public class YearlyRentalDetails extends BaseRentalDetails {
    YearlyRentalDetails(Date rentalStartDate) {
        super(rentalStartDate);
        createPaymentDueDates();
    }

    @Override
    public int getNumberOfMonths() {
        return 12;
    }
}
