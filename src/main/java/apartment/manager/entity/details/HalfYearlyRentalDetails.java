package apartment.manager.entity.details;

import jakarta.persistence.Embeddable;

import java.util.Date;

@Embeddable
public class HalfYearlyRentalDetails extends BaseRentalDetails {

    public HalfYearlyRentalDetails(Date rentalStartDate) {
        super(rentalStartDate);
        createPaymentDueDates();
    }

    @Override
    public int getNumberOfMonths() {
        return 6;
    }
}
