package apartment.manager.presentation.models;

public class StatisticsDto {
    private double rentedApartments = 5.0;
    private double vacatedApartments = 6.5;
    private double UnpaidOwedAmounts = 7.5;
    private double stat4 = 8.5;

    public double getRentedApartments() {
        return rentedApartments;
    }

    public void setRentedApartments(double rentedApartments) {
        this.rentedApartments = rentedApartments;
    }

    public double getVacatedApartments() {
        return vacatedApartments;
    }

    public void setVacatedApartments(double vacatedApartments) {
        this.vacatedApartments = vacatedApartments;
    }

    public double getUnpaidOwedAmounts() {
        return UnpaidOwedAmounts;
    }

    public void setUnpaidOwedAmounts(double unpaidOwedAmounts) {
        this.UnpaidOwedAmounts = unpaidOwedAmounts;
    }

    public double getStat4() {
        return stat4;
    }

    public void setStat4(double stat4) {
        this.stat4 = stat4;
    }
}