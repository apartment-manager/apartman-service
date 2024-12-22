package apartment.manager.entity.details;

import jakarta.persistence.Embeddable;

import java.util.Date;

@Embeddable
public class MonthlyRentalDetails extends BaseRentalDetails {
    MonthlyRentalDetails(Date rentalStartDate) {
        super(rentalStartDate);
        createPaymentDueDates();
    }

    @Override
    public int getNumberOfMonths() {
        return 1;
    }
}
