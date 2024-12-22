package apartment.manager.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Filter;

@Entity
@Table(name = "Tenants")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Filter(name = "userFilter", condition = "user_id  = :userId")
public class Tenant extends BaseEntity {
    private String name;
    private String idPhoto;
    private String phoneNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(String idPhoto) {
        this.idPhoto = idPhoto;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
