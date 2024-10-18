package apartment.manager.presentation.models;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class BuildingDto extends BaseDto {
    @NotBlank(message = "Building 'name' is a mandatory field")
    private String name;
    @NotBlank(message = "Building 'address' is a mandatory field")
    private String address;
    private int apartmentCount;
    private int floorsCount;
    private Double monthlyFees;
    private List<String> buildingImages;
    private int monthlyRentPrice;

    public BuildingDto(long id, String name, String address) {
        this.name = name;
        this.address = address;
        this.id = id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getApartmentCount() {
        return apartmentCount;
    }

    public void setApartmentCount(int apartmentCount) {
        this.apartmentCount = apartmentCount;
    }

    public int getFloorsCount() {
        return floorsCount;
    }

    public void setFloorsCount(int floorsCount) {
        this.floorsCount = floorsCount;
    }

    public Double getMonthlyFees() {
        return monthlyFees;
    }

    public void setMonthlyFees(Double monthlyFees) {
        this.monthlyFees = monthlyFees;
    }

    public List<String> getBuildingImages() {
        return buildingImages;
    }

    public void setBuildingImages(List<String> buildingImages) {
        this.buildingImages = buildingImages;
    }

    public int getMonthlyRentPrice() {
        return monthlyRentPrice;
    }

    public void setMonthlyRentPrice(int monthlyRentPrice) {
        this.monthlyRentPrice = monthlyRentPrice;
    }
}
