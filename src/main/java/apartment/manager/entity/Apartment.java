package apartment.manager.entity;

import apartment.manager.entity.details.BaseRentalDetails;
import apartment.manager.entity.utils.ApartmentType;
import jakarta.persistence.*;
import org.hibernate.annotations.Filter;

import static apartment.manager.entity.Apartment.APARTMENT_TABLE_NAME;

@Entity
@Filter(name = "userFilter", condition = BaseEntity.CREATED_BY_DATABASE_PROPERTY + "  = :createdBy")
@Table(name = APARTMENT_TABLE_NAME, uniqueConstraints = @UniqueConstraint(columnNames = {Apartment.NAME_APARTMENT_FIELD, Apartment.BUILDING_ID_APARTMENT_FIELD}))
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Apartment extends BaseEntity {
    public static final String APARTMENT_TABLE_NAME = "apartments";
    public static final String RENTAL_DETAILS_APARTMENT_FIELD = "rentalDetails";
    public static final String BUILDING_ID_APARTMENT_FIELD = "building_id";
    public static final String NAME_APARTMENT_FIELD = "name";
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = BUILDING_ID_APARTMENT_FIELD)
    private Building building;
    @Column
    private String name;
    @Column
    private ApartmentType type;
    @Column
    private String description;
    @Column
    private Boolean isAvailable;

    @Column
    @Embedded
    private BaseRentalDetails rentalDetails;

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

    public BaseRentalDetails getRentalDetails() {
        return rentalDetails;
    }

    public void setRentalDetails(BaseRentalDetails rentalDetails) {
        this.rentalDetails = rentalDetails;
    }
}
