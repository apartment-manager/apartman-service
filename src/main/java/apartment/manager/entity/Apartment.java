package apartment.manager.entity;

import apartment.manager.entity.utils.ApartmentType;
import apartment.manager.entity.utils.Currency;
import apartment.manager.entity.utils.PaymentDue;
import jakarta.persistence.*;

import static apartment.manager.entity.Apartment.TABLE_NAME;

@Entity
@Table(name = TABLE_NAME)
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
    private Currency currency; // TODO: handle mapping
    @Column
    private PaymentDue paymentDue; // TODO: handle mapping
    @Column
    private String contractPicture;
    @Column
    private Integer numberOfRooms;

    public Apartment(Building building, String name) {
        this.building = building;
        this.name = name;
    }

    public Apartment() {
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
}
