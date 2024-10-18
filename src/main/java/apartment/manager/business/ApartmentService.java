package apartment.manager.business;

import apartment.manager.Utilities.mappers.ApartmentMapper;
import apartment.manager.Utilities.models.GlobalException;
import apartment.manager.Utilities.models.GlobalExceptionCode;
import apartment.manager.entity.Apartment;
import apartment.manager.entity.Building;
import apartment.manager.presentation.models.ApartmentDto;
import apartment.manager.repo.ApartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ApartmentService { //TODO: implement service level validation for entities
    private final ApartmentRepository apartmentRepository;
    private BuildingService buildingService;
    @Autowired
    private ApartmentMapper apartmentMapper;

    public ApartmentService(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
    }

    public ApartmentDto createApartment(ApartmentDto apartmentDto) {
        Apartment apartment = apartmentMapper.apartmentDtoToApartment(apartmentDto);
        return apartmentMapper.apartmentToApartmentDto(apartmentRepository.save(apartment));
    }

    public ApartmentDto getApartmentById(Long id) {
        Apartment apartment = apartmentRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Couldn't find an apartment with id :" + id));
        return apartmentMapper.apartmentToApartmentDto(apartment);
    }

    public List<ApartmentDto> getApartmentsByBuildingId(Long id) {
        return apartmentMapper.allApartmentToApartmentDto(apartmentRepository.findByBuildingId(id));
    }

    public ApartmentDto updateApartment(ApartmentDto apartmentDto, Long id) { //TODO: handle updating only the changed fields
        if (!isExist(id)) {
            throw new GlobalException("Couldn't find a apartment with id: {" + id + "}", GlobalExceptionCode.RESOURCE_BUILDING_NOT_FOUND, NoSuchElementException.class);
        }
        Apartment apartment = apartmentMapper.apartmentDtoToApartment(apartmentDto);
        apartment.setId(id);
        Apartment updatedApartment = apartmentRepository.save(apartment);
        return apartmentMapper.apartmentToApartmentDto(updatedApartment);
    }

    public void deleteApartment(Long id) {
        if (!isExist(id)) {
            throw new GlobalException("Couldn't find a building with id: {" + id + "}", GlobalExceptionCode.RESOURCE_BUILDING_NOT_FOUND, NoSuchElementException.class);
        }
        ApartmentDto apartment = getApartmentById(id);
        apartmentRepository.delete(apartmentMapper.apartmentDtoToApartment(apartment));
    }

    public Long getNumberOfBuildingApartments(Building building, Long userId) {
        return apartmentRepository.countByBuildingAndUserId(building, userId);
    }

    public boolean isExist(long id) {
        return apartmentRepository.existsById(id);
    }

    public long getApartmentsCount() {
        return apartmentRepository.count();
    }
}
