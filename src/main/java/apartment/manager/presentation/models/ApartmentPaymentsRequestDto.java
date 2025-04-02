package apartment.manager.presentation.models;

import jakarta.validation.constraints.Positive;

public class ApartmentPaymentsRequestDto {
    @Positive(message = "Year must be a positive number")
    Integer year;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
