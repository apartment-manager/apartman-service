package apartment.manager.business;

import apartment.manager.Utilities.JwtAuthenticationFilter;
import apartment.manager.Utilities.mappers.ApartmentMapper;
import apartment.manager.Utilities.mappers.PaymentMapper;
import apartment.manager.Utilities.mappers.RentalDetailsMapper;
import apartment.manager.Utilities.models.GlobalException;
import apartment.manager.Utilities.models.GlobalExceptionCode;
import apartment.manager.entity.Apartment;
import apartment.manager.entity.Building;
import apartment.manager.entity.Tenant;
import apartment.manager.entity.details.BaseRentalDetails;
import apartment.manager.entity.details.RentalDetailsFactory;
import apartment.manager.entity.utils.Intervals;
import apartment.manager.presentation.models.ApartmentDto;
import apartment.manager.presentation.models.PaymentDto;
import apartment.manager.presentation.models.RentalDetailsDto;
import apartment.manager.repo.ApartmentRepository;
import apartment.manager.repo.TenantRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ApartmentService { //TODO: implement service level validation for entities
    private static final long THIRTY_ONE_DAYS = 1000 * 60 * 60 * 24 * 31L;
    private static final long TWENTY_SEVEN_DAYS = 1000 * 60 * 60 * 24 * 27L;
    private final ApartmentRepository apartmentRepository;
    @Autowired
    HttpSession session;
    @Autowired
    private ApartmentMapper apartmentMapper;
    @Autowired
    private RentalDetailsMapper rentalDetailsMapper;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private TenantRepository tenantRepository;
    @Autowired
    private PaymentMapper paymentMapper;

    public ApartmentService(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
    }

    public ApartmentDto createApartment(ApartmentDto apartmentDto) {
        Apartment apartment = apartmentMapper.apartmentDtoToApartment(apartmentDto);
        Apartment savedApartment;
        try {
            savedApartment = apartmentRepository.save(apartment);
        } catch (DataIntegrityViolationException exception) {
            throw new GlobalException("There exist an apartment with the name: {" + apartment.getName() + "} for the building: {" + apartment.getBuilding().getName() + "}", GlobalExceptionCode.UNIQUENESS, DataIntegrityViolationException.class);
        }
        return apartmentMapper.apartmentToApartmentDto(savedApartment);
    }

    public ApartmentDto getApartmentById(Long id) {
        Apartment apartment = getApartmentByIdWithoutMapping(id);
        return apartmentMapper.apartmentToApartmentDto(apartment);
    }

    private Apartment getApartmentByIdWithoutMapping(Long id) {
        return apartmentRepository.findByIdAndCreatedBy(id, (Long) session.getAttribute(JwtAuthenticationFilter.USER_ID_SESSION_ATTRIBUTE)).orElseThrow(() -> new GlobalException("Couldn't find an apartment with id: {" + id + "}", GlobalExceptionCode.RESOURCE_NOT_FOUND, NoSuchElementException.class));
    }

    public List<ApartmentDto> getApartmentsByBuildingId(Long id) {
        return apartmentMapper.allApartmentToApartmentDto(apartmentRepository.findByBuildingIdAndCreatedBy(id, (Long) session.getAttribute(JwtAuthenticationFilter.USER_ID_SESSION_ATTRIBUTE)));
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
        if (apartment.isAvailable()) {
            throw new GlobalException("Apartment " + apartment.getId() + " is already vacated", GlobalExceptionCode.VALIDATION, ValidationException.class);
        } else {
            apartment.setAvailable(true);
            Tenant tenant = apartment.getRentalDetails().getTenant();
            if (tenant != null) {
                tenantRepository.delete(tenant);
            }
            apartment.setRentalDetails(null);
            paymentService.deleteApartmentPayments(id);
            apartmentRepository.save(apartment);
        }
        return true;
    }

    /**
     * Updates the payment due dats for all the apartments, the job runs every twenty
     */
    @Scheduled(fixedRate = TWENTY_SEVEN_DAYS, initialDelay = 1000 * 60)
    public void updateApartmentPaymentDueDatesJob() {
        System.out.println("Starting scheduled job at:" + new Date());
        List<Apartment> apartments = apartmentRepository.findAll();
        for (Apartment apartment : apartments) {
            if (apartment.getRentalDetails() != null && apartment.getRentalDetails().getPaymentDueDates() != null && !apartment.getRentalDetails().getPaymentDueDates().isEmpty()) {
                Date lastPaymentDueDate = apartment.getRentalDetails().getPaymentDueDates().getLast();
                Date oneMonthLater = new Date(new Date().getTime() + THIRTY_ONE_DAYS);
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
        Apartment oldApartment = apartmentRepository.findByIdAndCreatedBy(id, (Long) session.getAttribute(JwtAuthenticationFilter.USER_ID_SESSION_ATTRIBUTE)).orElseThrow(() -> new GlobalException("Couldn't find an apartment with id: {" + id + "}", GlobalExceptionCode.RESOURCE_NOT_FOUND, NoSuchElementException.class));
        Apartment updatedApartment = apartmentMapper.apartmentDtoToApartment(apartmentDto);

        updatedApartment.setId(id);
        updatedApartment.setCreateDate(oldApartment.getCreateDate());
        updatedApartment.setModifiedDate(oldApartment.getModifiedDate());
        updatedApartment.setCreatedBy(oldApartment.getCreatedBy());

        Apartment savedApartment = apartmentRepository.save(updatedApartment);
        return apartmentMapper.apartmentToApartmentDto(savedApartment);
    }

    public List<ApartmentDto> searchApartments(String query) {
        return apartmentMapper.allApartmentToApartmentDto(apartmentRepository.findByNameContainingIgnoreCaseAndCreatedBy(query, (Long) session.getAttribute(JwtAuthenticationFilter.USER_ID_SESSION_ATTRIBUTE)));
    }

    @Transactional
    public void deleteApartment(Long id) {// TODO: Deleting an apartment should delete it's notifications
        if (!isExist(id)) {
            throw new GlobalException("Couldn't find an apartment with id: {" + id + "}", GlobalExceptionCode.RESOURCE_NOT_FOUND, NoSuchElementException.class);
        }
        paymentService.deleteApartmentPayments(id);
        apartmentRepository.deleteById(id);
    }

    public Long getNumberOfBuildingApartments(Building building) {
        return apartmentRepository.countByBuildingAndCreatedBy(building, (Long) session.getAttribute(JwtAuthenticationFilter.USER_ID_SESSION_ATTRIBUTE));
    }

    public int getNumberOfRentedApartments() {
        return apartmentRepository.countByIsAvailableFalseAndCreatedBy((Long) session.getAttribute(JwtAuthenticationFilter.USER_ID_SESSION_ATTRIBUTE));
    }

    public int getNumberOfVacatedApartments() {
        return apartmentRepository.countByIsAvailableTrueAndCreatedBy((Long) session.getAttribute(JwtAuthenticationFilter.USER_ID_SESSION_ATTRIBUTE));
    }

    public int getUnpaidOwedAmounts() {
        List<Apartment> apartments = apartmentRepository.findAllByCreatedBy((Long) session.getAttribute(JwtAuthenticationFilter.USER_ID_SESSION_ATTRIBUTE));
        List<PaymentDto> payments;
        int unpaidOwedAmounts = 0;
        for (Apartment apartment : apartments) {
            if (!apartment.isAvailable()) {
                Calendar calendar = Calendar.getInstance();
                int currentYear = calendar.get(Calendar.YEAR);
                payments = paymentService.getYearlyPaymentsByApartmentId(apartment.getId(), currentYear);
                if (payments != null && !payments.isEmpty() && payments.getLast().getMonth() != calendar.get(Calendar.MONTH)) {
                    apartment.getRentalDetails().getMonthlyRentValue();
                }
            }
        }
        return unpaidOwedAmounts;
    }

    public boolean isExist(long id) {
        return apartmentRepository.existsById(id);
    }
}
