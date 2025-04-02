package apartment.manager.entity.details;

import apartment.manager.entity.utils.Intervals;

import java.util.Date;

public class RentalDetailsFactory {
    public static BaseRentalDetails getRentalDetails(Intervals paymentSchedules, Date rentalStartDate) {
        return new BaseRentalDetails(rentalStartDate);
//                switch (paymentSchedules) {
//            case MONTHLY -> new MonthlyRentalDetails(rentalStartDate);
////            case HALF_YEARLY -> new HalfYearlyRentalDetails(rentalStartDate);
////            case YEARLY -> new YearlyRentalDetails(rentalStartDate);
//            default -> throw new RuntimeException(paymentSchedules.name() + " payment schedule is not supported.");
//        };
    }
}
