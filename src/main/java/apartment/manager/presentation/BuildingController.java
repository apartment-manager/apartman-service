package apartment.manager.presentation;

import apartment.manager.Utilities.models.GlobalException;
import apartment.manager.Utilities.models.GlobalExceptionCode;
import apartment.manager.business.BuildingService;
import apartment.manager.presentation.models.BuildingDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(path = "/buildings")
public class BuildingController {
    private final BuildingService buildingService;

    public BuildingController(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BuildingDto> createBuilding(@RequestBody @Valid BuildingDto building) {
        BuildingDto savedBuilding = buildingService.createBuilding(building);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest() // Builds URI based on the current request (i.e., /buildings)
                .path("/{id}")         // Appends the ID of the created resource to the URI
                .buildAndExpand(savedBuilding.getId()) // Replaces the placeholder with the new building ID
                .toUri();
        return ResponseEntity.created(location).body(savedBuilding);
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BuildingDto> getBuilding(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(buildingService.getBuildingById(id));
    }

    @GetMapping(path = "/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BuildingDto>> getAllBuildings() {
        return ResponseEntity.ok().body(buildingService.getAllBuildings());
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BuildingDto> updateBuilding(@PathVariable Long id, @RequestBody @Valid BuildingDto building) {
        return ResponseEntity.ok().body(buildingService.updateBuilding(building, id));
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteBuilding(@PathVariable Long id) {

        buildingService.deleteBuilding(id);
        return ResponseEntity.ok("Building {" + id + "} was deleted successfully");
    }
}
