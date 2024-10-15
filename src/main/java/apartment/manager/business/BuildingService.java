package apartment.manager.business;

import apartment.manager.Utilities.models.GlobalException;
import apartment.manager.Utilities.models.GlobalExceptionCode;
import apartment.manager.entity.Building;
import apartment.manager.presentation.models.BuildingDto;
import apartment.manager.repo.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Validated
public class BuildingService {
    private BuildingRepository buildingRepository;
    @Autowired
    public BuildingService (BuildingRepository buildingRepository){
        this.buildingRepository = buildingRepository;
    }

    public Building createBuilding(BuildingDto building){
        return buildingRepository.save(new Building(building.getName(), building.getAddress()));
    }

    public Building getBuilding(Long id) {
        return buildingRepository.findById(id).orElseThrow(() -> new GlobalException("Couldn't find building with id :" + id, GlobalExceptionCode.ERROR_001, NoSuchElementException.class));
    }

    public List<Building> getAllBuildings(){
       return buildingRepository.findAll();
    }

    public Building updateBuilding(BuildingDto building){
        return buildingRepository.save(new Building(building.getName(), building.getAddress()));
    }

}
