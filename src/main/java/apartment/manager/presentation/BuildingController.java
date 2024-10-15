package apartment.manager.presentation;

import apartment.manager.business.BuildingService;
import apartment.manager.entity.Building;
import apartment.manager.presentation.models.BuildingDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/buildings")
public class BuildingController {
    private final BuildingService buildingService;

    public BuildingController(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createBuilding(@RequestBody @Valid BuildingDto building) {
        buildingService.createBuilding(building);
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Building> getBuilding(@PathVariable("id") Long id) {
        return ResponseEntity.accepted().body(buildingService.getBuilding(id));
    }

    @GetMapping(path = "/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Building>> getAllBuildings() {
        return ResponseEntity.accepted().body(buildingService.getAllBuildings());
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Building> updateBuilding(@RequestBody @Valid BuildingDto building) {
        return ResponseEntity.accepted().body(buildingService.updateBuilding(building));
    }
}
