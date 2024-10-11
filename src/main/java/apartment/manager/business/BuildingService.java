package apartment.manager.business;

import apartment.manager.model.Building;
import apartment.manager.repo.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class BuildingService {
    private BuildingRepository buildingRepository;
    @Autowired
    public BuildingService (BuildingRepository buildingRepository){
        this.buildingRepository = buildingRepository;
    }

    public Building createBuilding(String name, String address){
        return buildingRepository.save(new Building(name,address));
    }

    public Building getBuilding(Long id) {
        return buildingRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Couldn't find building with id :" + id));
    }
}
