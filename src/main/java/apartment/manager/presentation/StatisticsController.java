package apartment.manager.presentation;

import apartment.manager.presentation.models.StatisticsDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/statistics")
public class StatisticsController {

    public StatisticsController() {
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<StatisticsDto> getPayment() {
        StatisticsDto statisticsDto = new StatisticsDto();
        return ResponseEntity.ok().body(statisticsDto);
    }
}
