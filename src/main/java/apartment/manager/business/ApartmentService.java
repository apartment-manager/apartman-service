package apartment.manager.business;

import apartment.manager.model.Apartment;
import apartment.manager.model.Building;
import apartment.manager.repo.ApartmentRepository;
import apartment.manager.repo.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ApartmentService {
    private ApartmentRepository apartmentRepository;
    @Autowired
    private BuildingService buildingService;

    @Autowired
    public ApartmentService(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
    }

    public Apartment CreateApartment(String name, Long buildingId){
        Building building = buildingService.getBuilding(buildingId);
        Apartment apartment = new Apartment(building,name);
        return apartmentRepository.save(apartment);
    }

    public Apartment getApartmentById(long id){
        return apartmentRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Couldn't find an apartment with id :" + id));
    }
    public long getApartmentsCount(){
        return apartmentRepository.count();
    }
}
