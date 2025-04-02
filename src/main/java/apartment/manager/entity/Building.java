package apartment.manager.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Filter;

import java.util.List;

@Entity
@Table(name = "buildings")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Filter(name = "userFilter", condition = BaseEntity.CREATED_BY_DATABASE_PROPERTY + "  = :createdBy")
public class Building extends BaseEntity {
    public static final String APARTMENT_COUNT_FIELD_NAME = "apartmentCount";
    @Column
    private String name;
    @Column
    private String address;
    @Column
    private Long apartmentCount; //TODO: figure out how to fill this field
    @Column
    private Integer floorsCount;
    @Column
    private Double monthlyFees;
    @Column
    private List<String> buildingImages;
    @Column
    private Integer monthlyRentPrice;

    public Building() {
    }

    public Building(String name, String address) {
        this.name = name;
        this.address = address;
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

    public Long getApartmentCount() {
        return apartmentCount;
    }

    public void setApartmentCount(Long apartmentCount) {
        this.apartmentCount = apartmentCount;
    }

    public Integer getFloorsCount() {
        return floorsCount;
    }

    public void setFloorsCount(Integer floorsCount) {
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

    public Integer getMonthlyRentPrice() {
        return monthlyRentPrice;
    }

    public void setMonthlyRentPrice(Integer monthlyRentPrice) {
        this.monthlyRentPrice = monthlyRentPrice;
    }

    //
//    public List<Apartment> getApartments() {
//        return apartments;
//    }
//
//    public void setApartments(List<Apartment> apartments) {
//        this.apartments = apartments;
//    }
}
