package apartment.manager.presentation.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.util.Date;

public class ApartmentDto extends BaseDto {
    @NotBlank(message = "Apartment 'name' is a mandatory field")
    private String name;
    @NotNull(message = "Apartment 'buildingId' is a mandatory field")
    @Positive
    private Long buildingId;
    @Pattern(regexp = "^(APARTMENT|STORE)$", message = "Type must be either APARTMENT or STORE")
    private String type;
    private String description;
    private Integer monthlyRentValue;
    private boolean isAvailable;
    private Long tenantId;
    @Pattern(regexp = "^(NIS|JOD|USD)$", message = "Currency must be either NIS, JOD, or USD")
    private String currency;
    @Pattern(regexp = "^(YEARLY|MONTHLY|HALF_YEARLY)$", message = "Payment due must be either YEARLY, HALF_YEARLY, or MONTHLY")
    private String paymentDue;
    private String contractPicture;
    private Integer numberOfRooms;
    private Date rentingStartDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMonthlyRentValue() {
        return monthlyRentValue;
    }

    public void setMonthlyRentValue(Integer monthlyRentValue) {
        this.monthlyRentValue = monthlyRentValue;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPaymentDue() {
        return paymentDue;
    }

    public void setPaymentDue(String paymentDue) {
        this.paymentDue = paymentDue;
    }

    public String getContractPicture() {
        return contractPicture;
    }

    public void setContractPicture(String contractPicture) {
        this.contractPicture = contractPicture;
    }

    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(Integer numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public Date getRentingStartDate() {
        return rentingStartDate;
    }

    public void setRentingStartDate(Date rentingStartDate) {
        this.rentingStartDate = rentingStartDate;
    }
}