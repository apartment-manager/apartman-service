package apartment.manager.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="buildings")
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name="building_id")
    private Long id;
    @Column
    private String name;
    @Column
    private String address;
    @Column
    @OneToMany(mappedBy = "building",cascade = CascadeType.ALL, orphanRemoval = true)

    private List<Apartment> apartments;

    public Building() {
    }

    public Building( String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public List<Apartment> getApartments() {
        return apartments;
    }

    public void setApartments(List<Apartment> apartments) {
        this.apartments = apartments;
    }
}
