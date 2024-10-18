package apartment.manager.Utilities.mappers;

import apartment.manager.business.BuildingService;
import apartment.manager.entity.Apartment;
import apartment.manager.entity.Building;
import apartment.manager.presentation.models.ApartmentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public abstract class ApartmentMapper {

    @Autowired
    BuildingService buildingService;
    @Autowired
    BuildingMapper buildingMapper;

    @Mapping(target = "buildingId", source = "building.id")
    public abstract ApartmentDto apartmentToApartmentDto(Apartment apartment);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "modifiedDate", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "building", source = "buildingId", qualifiedByName = "getBuildingById")
    public abstract Apartment apartmentDtoToApartment(ApartmentDto apartmentDto);

    public List<ApartmentDto> allApartmentToApartmentDto(List<Apartment> apartments) {
        return apartments.stream().map(this::apartmentToApartmentDto).collect(Collectors.toList());
    }

    public List<Apartment> allApartmentDtoToApartment(List<ApartmentDto> apartmentsDto) {
        return apartmentsDto.stream().map(this::apartmentDtoToApartment).collect(Collectors.toList());
    }

    @Named("getBuildingById")
    public Building getBuildingById(Long buildingId) {
        Building building = buildingMapper.buildingDtoToBuilding(buildingService.getBuildingById(buildingId));
        building.setId(buildingId);
        return building;
    }
}
