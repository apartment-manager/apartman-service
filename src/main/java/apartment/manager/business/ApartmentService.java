package apartment.manager.business;

import apartment.manager.Utilities.mappers.ApartmentMapper;
import apartment.manager.Utilities.models.GlobalException;
import apartment.manager.Utilities.models.GlobalExceptionCode;
import apartment.manager.entity.Apartment;
import apartment.manager.entity.Building;
import apartment.manager.presentation.models.ApartmentDto;
import apartment.manager.repo.ApartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
        Apartment savedApartment;
        try {
            savedApartment = apartmentRepository.save(apartment);
        } catch (DataIntegrityViolationException exception) {
            throw new GlobalException("There exist an apartment with the name: {" + apartment.getName() + "} for the building: {" + apartment.getBuilding().getName() + "}", GlobalExceptionCode.UNIQUENESS, NoSuchElementException.class);
        }
        return apartmentMapper.apartmentToApartmentDto(savedApartment);
    }

    public ApartmentDto getApartmentById(Long id) {
        Apartment apartment = apartmentRepository.findById(id).orElseThrow(() -> new GlobalException("Couldn't find an apartment with id: {" + id + "}", GlobalExceptionCode.RESOURCE_BUILDING_NOT_FOUND, NoSuchElementException.class));
        return apartmentMapper.apartmentToApartmentDto(apartment);
    }

    public List<ApartmentDto> getApartmentsByBuildingId(Long id) {
        return apartmentMapper.allApartmentToApartmentDto(apartmentRepository.findByBuildingId(id));
    }

    public ApartmentDto updateApartment(ApartmentDto apartmentDto, Long id) {
        Apartment oldApartment = apartmentRepository.findById(id).orElseThrow(() -> new GlobalException("Couldn't find an apartment with id: {" + id + "}", GlobalExceptionCode.RESOURCE_BUILDING_NOT_FOUND, NoSuchElementException.class));
        Apartment updatedApartment = apartmentMapper.apartmentDtoToApartment(apartmentDto);

        updatedApartment.setId(id);
        updatedApartment.setCreateDate(oldApartment.getCreateDate());
        updatedApartment.setModifiedDate(oldApartment.getModifiedDate());
        updatedApartment.setUserId(oldApartment.getUserId());

        Apartment savedApartment = apartmentRepository.save(updatedApartment);
        return apartmentMapper.apartmentToApartmentDto(savedApartment);
    }

    public List<ApartmentDto> searchApartments(String query) {
        return apartmentMapper.allApartmentToApartmentDto(apartmentRepository.findByNameContainingIgnoreCase(query));
    }

    public void deleteApartment(Long id) {// TODO: Deleting an apartment should delete it's payments
        if (!isExist(id)) {
            throw new GlobalException("Couldn't find an apartment with id: {" + id + "}", GlobalExceptionCode.RESOURCE_BUILDING_NOT_FOUND, NoSuchElementException.class);
        }
        apartmentRepository.deleteById(id);
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
