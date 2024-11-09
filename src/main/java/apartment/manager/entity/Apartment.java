package apartment.manager.entity;

import apartment.manager.entity.utils.ApartmentType;
import apartment.manager.entity.utils.Currency;
import apartment.manager.entity.utils.PaymentDue;
import jakarta.persistence.*;

import java.util.Date;

import static apartment.manager.entity.Apartment.TABLE_NAME;

@Entity
@Table(name = TABLE_NAME, uniqueConstraints = @UniqueConstraint(columnNames = {"name", "building_id"}))
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Apartment extends BaseEntity {
    public static final String TABLE_NAME = "apartments";
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id")
    private Building building;
    @Column
    private String name;
    @Column
    private ApartmentType type;
    @Column
    private String description;
    @Column
    private Integer monthlyRentValue;
    @Column
    private boolean isAvailable;
    @Column
    private Long tenantId; //TODO: create Tenant Entity and handle mapping
    @Column
    private Currency currency; // TODO: add proper validation message to show the acceptable values
    @Column
    private PaymentDue paymentDue; // TODO: add proper validation message to show the acceptable values
    @Column
    private String contractPicture;
    @Column
    private Integer numberOfRooms;
    @Column
    private Date rentingStartDate;

    public Apartment(Building building, String name) {
        this.building = building;
        this.name = name;
    }

    public Apartment() {
    }

    @PrePersist
    public void setDefaults() {
        if (this.currency == null) {
            this.currency = Currency.NIS;
        }
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ApartmentType getType() {
        return type;
    }

    public void setType(ApartmentType type) {
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

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public PaymentDue getPaymentDue() {
        return paymentDue;
    }

    public void setPaymentDue(PaymentDue paymentDue) {
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
