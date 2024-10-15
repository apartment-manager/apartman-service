package apartment.manager.presentation.models;

import jakarta.validation.constraints.NotBlank;

public class BuildingDto {
    @NotBlank(message = "Building 'name' is a mandatory field")
    private String name;
    @NotBlank(message = "Building 'address' is a mandatory field")
    private String address;

    public BuildingDto( String name, String address) {
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
}
