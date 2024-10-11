package apartment.manager;

import apartment.manager.business.ApartmentService;
import apartment.manager.business.BuildingService;
import apartment.manager.model.Apartment;
import apartment.manager.model.Building;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApartmanApplication implements CommandLineRunner {
	@Autowired
	private ApartmentService apartmentService;
	@Autowired
	private BuildingService buildingService;


	public static void main(String[] args) {
		SpringApplication.run(ApartmanApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		Building building = buildingService.createBuilding("Building 1", "Jenin, Street 5");
		Building building2 = new Building("Building 2", "Jenin, Street 6");
		Apartment apartment = apartmentService.CreateApartment("Apartment 1 ", building.getId());
	}
}
