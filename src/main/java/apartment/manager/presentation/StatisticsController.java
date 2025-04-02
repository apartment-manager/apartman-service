package apartment.manager.presentation;

import apartment.manager.business.ApartmentService;
import apartment.manager.presentation.models.StatisticsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/statistics")
public class StatisticsController {

    @Autowired
    private ApartmentService apartmentService;

    public StatisticsController() {
    }

    @GetMapping(path = "/main-page-statistics")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<StatisticsDto> getMainPageStatistics() {
        StatisticsDto statisticsDto = new StatisticsDto();
        statisticsDto.setRentedApartments(apartmentService.getNumberOfRentedApartments());
        statisticsDto.setVacatedApartments(apartmentService.getNumberOfVacatedApartments());

        return ResponseEntity.ok().body(statisticsDto);
    }
}
