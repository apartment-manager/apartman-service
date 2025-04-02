package apartment.manager.presentation.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public class ApartmentDto extends BaseDto {
    @NotBlank(message = "Apartment 'name' is a mandatory field")
    private Integer name;
    @NotNull(message = "Apartment 'buildingId' is a mandatory field")
    @Positive
    private Long buildingId;
    @Pattern(regexp = "^(APARTMENT|STORE)$", message = "Type must be either APARTMENT or STORE")
    @NotNull(message = "Apartment 'type' is a mandatory field")
    private String type;
    private String description;
    private boolean isAvailable;
    private Integer numberOfRooms;
    private RentalDetailsDto rentalDetails;

    public Integer getName() {
        return name;
    }

    public void setName(Integer name) {
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

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(Integer numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public RentalDetailsDto getRentalDetails() {
        return rentalDetails;
    }

    public void setRentalDetails(RentalDetailsDto rentalDetails) {
        this.rentalDetails = rentalDetails;
    }}