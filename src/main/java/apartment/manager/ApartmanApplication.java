package apartment.manager;

import apartment.manager.business.ApartmentService;
import apartment.manager.business.BuildingService;
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
	}
}
