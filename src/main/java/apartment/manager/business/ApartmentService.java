package apartment.manager.business;

import apartment.manager.Utilities.mappers.ApartmentMapper;
import apartment.manager.Utilities.mappers.RentalDetailsMapper;
import apartment.manager.Utilities.models.GlobalException;
import apartment.manager.Utilities.models.GlobalExceptionCode;
import apartment.manager.business.models.BaseRentalDetails;
import apartment.manager.business.models.RentalDetailsFactory;
import apartment.manager.entity.Apartment;
import apartment.manager.entity.Building;
import apartment.manager.entity.utils.Intervals;
import apartment.manager.presentation.models.ApartmentDto;
import apartment.manager.presentation.models.RentalDetailsDto;
import apartment.manager.repo.ApartmentRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ApartmentService { //TODO: implement service level validation for entities
    private static final long thirtyOneDays = 1000 * 60 * 60 * 24 * 31L;
    private static final long thirtyDays = 1000 * 60 * 60 * 24 * 30L;
    private final ApartmentRepository apartmentRepository;
    private BuildingService buildingService;
    @Autowired
    private ApartmentMapper apartmentMapper;
    @Autowired
    private RentalDetailsMapper rentalDetailsMapper;
    @Autowired
    private PaymentService paymentService;

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
        Apartment apartment = getApartmentByIdWithoutMapping(id);
        return apartmentMapper.apartmentToApartmentDto(apartment);
    }

    private Apartment getApartmentByIdWithoutMapping(Long id) {
        return apartmentRepository.findById(id).orElseThrow(() -> new GlobalException("Couldn't find an apartment with id: {" + id + "}", GlobalExceptionCode.RESOURCE_NOT_FOUND, NoSuchElementException.class));
    }

    public List<ApartmentDto> getApartmentsByBuildingId(Long id) {
        return apartmentMapper.allApartmentToApartmentDto(apartmentRepository.findByBuildingId(id));
    }

    public boolean rentApartment(Long id, RentalDetailsDto rentalDetailsDto) {
        Apartment apartment = getApartmentByIdWithoutMapping(id);
        if (!apartment.isAvailable()) {
            throw new GlobalException("Apartment " + apartment.getId() + " is already rented", GlobalExceptionCode.VALIDATION, ValidationException.class);
        }
        BaseRentalDetails rentalDetails = RentalDetailsFactory.getRentalDetails(Intervals.valueOf(Intervals.MONTHLY.name()), rentalDetailsDto.getRentalStartDate());
        rentalDetailsMapper.rentalDetailsDtoToRentalDetails(rentalDetailsDto, rentalDetails);
        apartment.setRentalDetails(rentalDetails);
        apartment.setAvailable(false);
        apartmentRepository.save(apartment);
        return true;
    }

    @Transactional
    public boolean vacateApartment(Long id) {
        Apartment apartment = getApartmentByIdWithoutMapping(id);
        apartment.setAvailable(true);
        apartment.setRentalDetails(null);
        paymentService.deleteApartmentPayments(id);
        apartmentRepository.save(apartment);

        // Todo: remove payments and tenant
        return true;
    }

    /**
     * Updates the payment due dats for all the apartments
     */
    @Scheduled(fixedRate = thirtyDays, initialDelay = 1000 * 60)
    public void updateApartmentPaymentDueDatesJob() {
        System.out.println("Starting scheduled job at:" + new Date());
        List<Apartment> apartments = apartmentRepository.findAll();
        for (Apartment apartment : apartments) {
            if (apartment.getRentalDetails() != null && apartment.getRentalDetails().getPaymentDueDates() != null && !apartment.getRentalDetails().getPaymentDueDates().isEmpty()) {
                Date lastPaymentDueDate = apartment.getRentalDetails().getPaymentDueDates().getLast();
                Date oneMonthLater = new Date(new Date().getTime() + thirtyOneDays);
                if (lastPaymentDueDate.before(oneMonthLater)) {
                    apartment.getRentalDetails().addTwoPaymentDueDates(lastPaymentDueDate);
                    apartmentRepository.save(apartment);
                    System.out.println("Updated apartment " + apartment.getId() + " payment due dates");
                }
            }
        }
        System.out.println("Finished scheduled job at:" + new Date());
    }

    public ApartmentDto updateApartment(ApartmentDto apartmentDto, Long id) {
        Apartment oldApartment = apartmentRepository.findById(id).orElseThrow(() -> new GlobalException("Couldn't find an apartment with id: {" + id + "}", GlobalExceptionCode.RESOURCE_NOT_FOUND, NoSuchElementException.class));
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

    public void deleteApartment(Long id) {// TODO: Deleting an apartment should delete it's payments and notifications
        if (!isExist(id)) {
            throw new GlobalException("Couldn't find an apartment with id: {" + id + "}", GlobalExceptionCode.RESOURCE_NOT_FOUND, NoSuchElementException.class);
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
