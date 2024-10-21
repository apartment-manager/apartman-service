package apartment.manager.Utilities.mappers;

import apartment.manager.entity.Building;
import apartment.manager.presentation.models.BuildingDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public abstract class BuildingMapper {

    public abstract BuildingDto buildingToBuildingDto(Building building);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "modifiedDate", ignore = true)
    @Mapping(target = "userId", ignore = true)
    public abstract Building buildingDtoToBuilding(BuildingDto buildingDto);

    public List<BuildingDto> allBuildingToBuildingDto(List<Building> buildings) {
        return buildings.stream().map(this::buildingToBuildingDto).collect(Collectors.toList());
    }

    public List<Building> allBuildingDtoToBuilding(List<BuildingDto> buildingsDto) {
        return buildingsDto.stream().map(this::buildingDtoToBuilding).collect(Collectors.toList());
    }
}
