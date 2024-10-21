package apartment.manager.business;

import apartment.manager.Utilities.mappers.BuildingMapper;
import apartment.manager.Utilities.models.GlobalException;
import apartment.manager.Utilities.models.GlobalExceptionCode;
import apartment.manager.entity.Building;
import apartment.manager.presentation.models.BuildingDto;
import apartment.manager.repo.ApartmentRepository;
import apartment.manager.repo.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Validated
public class BuildingService { // TODO: implement service level validation for entities
    private final BuildingRepository buildingRepository;
    public BuildingMapper buildingMapper;
    public ApartmentRepository apartmentRepository;// TODO: use apartment service instead

    @Autowired
    public BuildingService(BuildingRepository buildingRepository, ApartmentRepository apartmentRepository, BuildingMapper buildingMapper) {
        this.buildingRepository = buildingRepository;
        this.apartmentRepository = apartmentRepository;
        this.buildingMapper = buildingMapper;
    }

    public BuildingDto createBuilding(BuildingDto buildingDto) {
        Building building = buildingMapper.buildingDtoToBuilding(buildingDto);
        building.setCreateDate(new Date());
        return buildingMapper.buildingToBuildingDto(buildingRepository.save(building));
    }

    public BuildingDto getBuildingById(Long id) {
        Building building = buildingRepository.findById(id).orElseThrow(() -> new GlobalException("Couldn't find building with id :" + id, GlobalExceptionCode.RESOURCE_BUILDING_NOT_FOUND, NoSuchElementException.class));
        return buildingMapper.buildingToBuildingDto(building);
    }

    public List<BuildingDto> getAllBuildings() {
        List<Building> buildings = buildingRepository.findAll();
        buildings.forEach(building -> {// TODO: handle getting user Id from session
            building.setApartmentCount(apartmentRepository.countByBuildingAndUserId(building, 1L));
        });
        return buildingMapper.allBuildingToBuildingDto(buildings);
    }

    public BuildingDto updateBuilding(BuildingDto buildingDto, Long id) {
        Building oldBuilding = buildingRepository.findById(id).orElseThrow(() -> new GlobalException("Couldn't find a building with id: {" + id + "}", GlobalExceptionCode.RESOURCE_BUILDING_NOT_FOUND, NoSuchElementException.class));
        Building updatedBuilding = buildingMapper.buildingDtoToBuilding(buildingDto);

        updatedBuilding.setId(id);
        updatedBuilding.setCreateDate(oldBuilding.getCreateDate());
        updatedBuilding.setModifiedDate(oldBuilding.getModifiedDate());
        updatedBuilding.setUserId(oldBuilding.getUserId());

        Building savedBuilding = buildingRepository.save(updatedBuilding);
        return buildingMapper.buildingToBuildingDto(savedBuilding);
    }

    public boolean isExist(long id) {
        return buildingRepository.existsById(id);
    }

    public void deleteBuilding(Long id) {// TODO: Deleting a building should delete it's apartments
        if (!isExist(id)) {
            throw new GlobalException("Couldn't find a building with id: {" + id + "}", GlobalExceptionCode.RESOURCE_BUILDING_NOT_FOUND, NoSuchElementException.class);
        }
        buildingRepository.deleteById(id);
    }
}
